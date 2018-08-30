package app.database;

import app.database.entities.BinaryOperation;
import app.database.entities.Constants;
import app.database.entities.Operation;
import app.database.entities.Sessions;
import app.database.entities.SingleOperation;
import app.database.entities.dto.OperationDto;
import app.database.entities.dto.SingleOperationDto;
import app.rest.Key;
import app.rest.UpdatePost;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.OrderBy;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class JDBC {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private HttpServletRequest req;
    @Autowired
    private Logger rootLogger;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SessionFactory sessionFactory;


    JDBC() {
    }

    @PostConstruct
    public void init() {
        System.out.println("JDBCExample postConstruct is called. datasource = " + dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void putOperation(Operation operation) {
        sessionFactory.getCurrentSession().save(operation.toDto());
    }

    @Transactional
    public void putSession() {
        Sessions sessions = new Sessions();
        sessions.setId(req.getSession().getId());
        sessions.setIp(req.getRemoteAddr());
        sessions.setTimeStart(new Timestamp(req.getSession().getCreationTime()));
        sessions.setTimeEnd(new Timestamp(req.getSession().getLastAccessedTime()));
        sessionFactory.getCurrentSession().save(sessions);
        rootLogger.info("В базу даных добалена новая сессия с ID: " + req.getSession().getId());
    }

    @Transactional
    public void updateSession() {
        Sessions sessions = sessionFactory.getCurrentSession().get(Sessions.class, req.getSession().getId());
        sessions.setTimeEnd(new Timestamp(req.getSession().getLastAccessedTime()));
        sessionFactory.getCurrentSession().saveOrUpdate(sessions);
        rootLogger.info("В базе данных обновлена сессия с ID: " + req.getSession().getId());
    }


    @Transactional
    public void putConstInDB(Constants constant) {
        sessionFactory.getCurrentSession().save(constant);
    }

    @Transactional
    public void updatePostDB(UpdatePost post) {
        Constants constants = sessionFactory.getCurrentSession().get(Constants.class, post.getKeyOld());
        constants.setKey(post.getKeyNew());
        constants.setValue(post.getValue());
        sessionFactory.getCurrentSession().update(constants);
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
        List<Sessions> resultList = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
        for (Sessions sessions:resultList){
            System.out.println("Операции для сессии: "+sessions.getOperations());
        }
        return resultList;
    }

    @Transactional
    public List<OperationDto> selectDataFromBD(String mode, String order, String id) {
        Sessions session = sessionFactory.getCurrentSession().get(Sessions.class, id);
        List<OperationDto> operations = session.getOperations();
        if ("ASC".equals(order.toUpperCase())) {
            switch (mode){
                case "operationKind":
                    operations.sort(Comparator.comparing(OperationDto::getOperationKind));break;
                case "time":
                    operations.sort(Comparator.comparing(OperationDto::getTime));break;
            }
        } else {
            switch (mode){
                case "operationKind":
                    operations.sort(Comparator.comparing(OperationDto::getOperationKind).reversed());break;
                case "time":
                    operations.sort(Comparator.comparing(OperationDto::getTime).reversed());break;
            }
        }


        return operations;
    }
}
