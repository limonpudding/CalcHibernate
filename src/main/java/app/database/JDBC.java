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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
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

    @Transactional//TODO не работает занесение в БД
    public void putOperation(Operation operation) {
        sessionFactory.getCurrentSession().save(operation.toDto());//не, ну а вдруг заработает
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
        CriteriaQuery<Sessions> criteria = sessionFactory.getCurrentSession().getCriteriaBuilder().createQuery(Sessions.class);
        if ("ASC".equals(order.toUpperCase())) {
            criteria.orderBy((Order) org.hibernate.criterion.Order.asc(mode));
        } else {
            criteria.orderBy((Order) org.hibernate.criterion.Order.desc(mode));
        }
        criteria.from(Sessions.class);
        return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
    }

    @Transactional
    public List<OperationDto> selectDataFromBD(String mode, String order, String id) {//TODO: добавить выборку только по нужному id (where)
        CriteriaQuery<OperationDto> criteria = sessionFactory.getCurrentSession().getCriteriaBuilder().createQuery(OperationDto.class);
        criteria.from(OperationDto.class);
        return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
    }
}
