package app.database;

import app.database.entities.*;
import app.database.entities.dao.OperationDao;
import app.database.entities.dto.UsersDto;
import app.database.entities.dto.UsersMapper;
import app.rest.Key;
import app.rest.UpdatePost;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import static app.utils.Log.*;

@Repository
public class JDBC {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private HttpServletRequest req;
    @Autowired
    private Logger rootLogger;
    @Autowired
    private SessionFactory sessionFactory;

    JDBC() {}

    @PostConstruct
    public void init() {
        System.out.println("JDBCExample postConstruct is called. datasource = " + dataSource);
    }

    @Transactional
    public void putOperation(Operation operation) {
        sessionFactory.getCurrentSession().save(operation.toDto());
    }


    @Transactional
    public List<UsersDto> selectUsersFromBD() {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
        criteria.from(Users.class);
        List<UsersDto> usersDtos = new LinkedList<>();
        for (Users user : sessionFactory.getCurrentSession().createQuery(criteria).getResultList()) {
            Hibernate.initialize(user.getUserroles());
            usersDtos.add(UsersMapper.INSTANCE.toDto(user));
        }
        return usersDtos;
    }

    @Transactional
    public void putSession() {
        Sessions sessions = new Sessions(
                req.getSession().getId(),
                req.getRemoteAddr());
        sessionFactory.getCurrentSession().save(sessions);
        print(rootLogger, Level.INFO, "В базу даных добалена новая сессия с ID: {}", req.getSession().getId());
    }

    @Transactional
    public void updateSession() {
        Sessions sessions = sessionFactory.getCurrentSession().get(Sessions.class, req.getSession().getId());
        if (sessions == null) {
            putSession();
            return;
        }
        sessions.setTimeEnd(new Timestamp(req.getSession().getLastAccessedTime()));
        sessionFactory.getCurrentSession().update(sessions);
        print(rootLogger, Level.INFO, "В базе данных обновлена сессия с ID: {}", req.getSession().getId());
    }


    @Transactional
    public void putConstInDB(Constants constant) {
        sessionFactory.getCurrentSession().save(constant);
    }

    @Transactional
    public void putUserInDB(Users user) {
        sessionFactory.getCurrentSession().save(user);
        print(rootLogger, Level.INFO, "В БД добавлен пользователь с логином \'{}\' и паролем \'{}\'", user.getUsername(), user.getPassword());
    }

    @Transactional
    public void deleteUserRoleInDB(String username, Roles role) {
        Users user = sessionFactory.getCurrentSession().get(Users.class, username);
        user.deleteUserrole(role);
        sessionFactory.getCurrentSession().update(user);
        print(rootLogger, Level.INFO, "В БД убрано право пользователя с логином \'{}\' и паролем \'{}\'", username, role.getName());
    }

    @Transactional
    public void addUserRoleInDB(String username, Roles role) {
        Users user = sessionFactory.getCurrentSession().get(Users.class, username);
        user.addUserrole(role);
        sessionFactory.getCurrentSession().update(user);
        print(rootLogger, Level.INFO, "В БД добавлено право пользователя с логином \'{}\' и паролем \'{}\'", username, role.getName());
    }

    @Transactional
    public void updatePostDB(UpdatePost post) {
        Constants constants = sessionFactory.getCurrentSession().get(Constants.class, post.getKeyOld());
        sessionFactory.getCurrentSession().delete(constants);
        constants.setKey(post.getKeyNew());
        constants.setValue(post.getValue());
        sessionFactory.getCurrentSession().save(constants);
    }

    @Transactional
    public void deleteConstantDB(Key key) {
        Constants constants = sessionFactory.getCurrentSession().get(Constants.class, key.getKey());
        sessionFactory.getCurrentSession().delete(constants);
    }

    @Transactional
    public void updatePatchDB(Constants constant) {
        Constants constants = sessionFactory.getCurrentSession().get(Constants.class, constant.getKey());
        constants.setValue(constant.getValue());
        sessionFactory.getCurrentSession().update(constants);
    }

    @Transactional
    public List<Constants> getConstantsDB() {
        CriteriaQuery<Constants> criteria = sessionFactory.getCurrentSession().getCriteriaBuilder().createQuery(Constants.class);
        criteria.from(Constants.class);
        return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
    }

    @Transactional
    public String getConstantValueDB(String key) {
        return sessionFactory.getCurrentSession().get(Constants.class, key).getValue();
    }

    @Transactional
    public List<Sessions> selectSessionsFromBD(String mode, String order) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Sessions> criteria = builder.createQuery(Sessions.class);
        Root criteriaRoot = criteria.from(Sessions.class);
        if ("ASC".equals(order.toUpperCase())) {
            criteria.orderBy(builder.asc(criteriaRoot.get(mode)));
        } else {
            criteria.orderBy(builder.desc(criteriaRoot.get(mode)));
        }
        return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
    }

    @Transactional
    public List<OperationDao> selectDataFromBD(String mode, String order, String id) {
        Sessions session = sessionFactory.getCurrentSession().get(Sessions.class, id);
        List<OperationDao> operations = session.getOperations();
        if ("ASC".equals(order.toUpperCase())) {
            switch (mode) {
                case "operationKind":
                    operations.sort(Comparator.comparing(OperationDao::getOperationKind));
                    break;
                case "time":
                    operations.sort(Comparator.comparing(OperationDao::getTime));
                    break;
            }
        } else {
            switch (mode) {
                case "operationKind":
                    operations.sort(Comparator.comparing(OperationDao::getOperationKind).reversed());
                    break;
                case "time":
                    operations.sort(Comparator.comparing(OperationDao::getTime).reversed());
                    break;
            }
        }
        return operations;
    }
}
