package app.database;

import app.database.entities.BinaryOperation;
import app.database.entities.Constants;
import app.database.entities.Sessions;
import app.database.entities.SingleOperation;
import app.rest.Key;
import app.rest.UpdatePost;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaQuery;
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
    public void putOperation(app.database.entities.Operation operation) {
        String BINARY_SQL = "INSERT INTO "
                + "BINARYOPERATION"
                + " (ID, NAME, FIRSTOPERAND, SECONDOPERAND, ANSWER, IDSESSION, TIME) VALUES (:id,:name,:firstoperand,:secondoperand,:answer,:idsession,:time)";
        String SINGLE_SQL = "INSERT INTO "
                + "SINGLEOPERATION"
                + " (ID, NAME, FIRSTOPERAND, ANSWER, IDSESSION, TIME) VALUES (:id,:name,:firstoperand,:answer,:idsession,:time)";
        if (operation instanceof SingleOperation) {
            SingleOperation singleOperation = (SingleOperation) operation;
            Session session = sessionFactory.openSession();
            session.createSQLQuery(SINGLE_SQL)
                    .setParameter("id", singleOperation.getId())
                    .setParameter("name", operation.getOperationKind().getKind())
                    .setParameter("firstoperand", singleOperation.getFirstOperand().toString())
                    .setParameter("answer", singleOperation.getAnswer().toString())
                    .setParameter("idSession", singleOperation.getIdsession())
                    .setParameter("time", singleOperation.getTime());
            session.close();
        } else {
            BinaryOperation binaryOperation = (BinaryOperation) operation;
            Session session = sessionFactory.openSession();
            session.createSQLQuery(BINARY_SQL)
                    .setParameter("id", binaryOperation.getId())
                    .setParameter("name", operation.getOperationKind().getKind())
                    .setParameter("firstoperand", binaryOperation.getFirstOperand().toString())
                    .setParameter("secondoperand", binaryOperation.getSecondOperand().toString())
                    .setParameter("answer", binaryOperation.getAnswer().toString())
                    .setParameter("idSession", binaryOperation.getIdsession())
                    .setParameter("time", binaryOperation.getTime());
            session.close();
        }
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

    public List<SessionsRow> selectSessionsFromBD(String mode, String order) {
        final String SELECT_SQL = "" +
                "select * from (select distinct sessions.id, sessions.ip,sessions.timestart,sessions.timeend, 'false' as operation from SESSIONS left join history on SESSIONS.id = HISTORY.id where operation is null\n" +
                "union all\n" +
                "select distinct sessions.id, sessions.ip,sessions.timestart,sessions.timeend, 'true' as operation from SESSIONS left join history on SESSIONS.id = HISTORY.id where operation is not null) order by " + mode + " " + order;

        Session session = sessionFactory.openSession();
        List<SessionsRow> dbRows = session.createSQLQuery(SELECT_SQL).list();
        session.close();
        return dbRows;
    }

    public List<OperationsRow> selectDataFromBD(String mode, String order, String id) {
        final String SELECT_SQL = "SELECT OPERATION, FIRSTOPERAND, SECONDOPERAND, ANSWER, TIME FROM HISTORY where '" + id + "'=ID ORDER BY " + mode + " " + order;
        Session session = sessionFactory.openSession();
        List<OperationsRow> dbRows = session.createSQLQuery(SELECT_SQL).list();
        session.close();
        return dbRows;
    }

    public class OperationsRow {
        private String operationName;
        private String op1;
        private String op2;
        private String answer;
        private String time;

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        public void setOp1(String op1) {
            this.op1 = op1;
        }

        public void setOp2(String op2) {
            this.op2 = op2;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String operationName() {
            return operationName;
        }

        public String op1() {
            return op1;
        }

        public String op2() {
            return op2;
        }

        public String answer() {
            return answer;
        }

        public String time() {
            return time;
        }
    }

    public class SessionsRow {
        private String id;
        private String ip;
        private String sessionStartTime;
        private String sessionEndTime;
        private String operation;

        public void setId(String id) {
            this.id = id;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setSessionStartTime(String sessionStartTime) {
            this.sessionStartTime = sessionStartTime;
        }

        public void setSessionEndTime(String sessionEndTime) {
            this.sessionEndTime = sessionEndTime;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String id() {
            return id;
        }

        public String ip() {
            return ip;
        }

        public String sessionStartTime() {
            return sessionStartTime;
        }

        public String sessionEndTime() {
            return sessionEndTime;
        }

        public String operation() {
            return operation;
        }
    }
}
