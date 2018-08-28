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

    public void putOperation(app.database.entities.Operation operation) {
        String BINARY_SQL = "INSERT INTO "
                + operation.getOperationKind().getTableName()
                + " (ID, FIRSTOPERAND, SECONDOPERAND, ANSWER, IDSESSION, TIME) VALUES (:id,:firstoperand,:secondoperand,:answer,:idsession,:time)";
        String SINGLE_SQL = "INSERT INTO "
                + operation.getOperationKind().getTableName()
                + " (ID, FIRSTOPERAND, ANSWER, IDSESSION, TIME) VALUES (:id,:firstoperand,:answer,:idsession,:time)";
        if (operation instanceof SingleOperation) {
            SingleOperation singleOperation = (SingleOperation) operation;
            Session session = sessionFactory.openSession();
            session.createSQLQuery(SINGLE_SQL)
                    .setParameter("id", singleOperation.getId())
                    .setParameter("firstoperand", singleOperation.getFirstOperand().toString())
                    .setParameter("answer", singleOperation.getAnswer().toString())
                    .setParameter("idsession", singleOperation.getIdsession())
                    .setParameter("time", singleOperation.getTime());
            session.close();
        } else {
            BinaryOperation binaryOperation = (BinaryOperation) operation;
            Session session = sessionFactory.openSession();
            session.createSQLQuery(BINARY_SQL)
                    .setParameter("id", binaryOperation.getId())
                    .setParameter("firstoperand", binaryOperation.getFirstOperand().toString())
                    .setParameter("secondoperand", binaryOperation.getSecondOperand().toString())
                    .setParameter("answer", binaryOperation.getAnswer().toString())
                    .setParameter("idsession", binaryOperation.getIdsession())
                    .setParameter("time", binaryOperation.getTime());
            session.close();
        }
    }

    public void putSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Sessions sessions = new Sessions();
        sessions.setId(req.getSession().getId());
        sessions.setIp(req.getRemoteAddr());
        sessions.setTimestart(new Timestamp(req.getSession().getCreationTime()));
        sessions.setTimeend(new Timestamp(req.getSession().getLastAccessedTime()));
        session.save(sessions);
        session.getTransaction().commit();
        session.close();
        rootLogger.info("В базу даных добалена новая сессия с ID: " + req.getSession().getId());
    }

    @Transactional
    public void updateSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Sessions sessions = session.get(Sessions.class, req.getSession().getId());
        sessions.setTimeend(new Timestamp(req.getSession().getLastAccessedTime()));
        session.saveOrUpdate(sessions);
        session.getTransaction().commit();
        session.close();
        rootLogger.info("В базе данных обновлена сессия с ID: " + req.getSession().getId());
    }


    public void putConstInDB(Constants constant) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(constant);
        session.getTransaction().commit();
        session.close();
    }

    public void updatePostDB(UpdatePost post) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Constants constants = session.get(Constants.class, post.getKeyOld());
        constants.setKey(post.getKeyNew());
        constants.setValue(post.getValue());
        session.saveOrUpdate(constants);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteConstantDB(Key key) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Constants constants = session.get(Constants.class, key.getKey());
        session.delete(constants);
        session.getTransaction().commit();
        session.close();
    }

    public void updatePatchDB(Constants constant) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Constants constants = session.get(Constants.class, constant.getKey());
        constants.setValue(constant.getValue());
        session.saveOrUpdate(constants);
        session.getTransaction().commit();
        session.close();
    }

    public List getConstantsDB() {
        final String SELECT_SQL = "SELECT CONSTANTS.KEY, CONSTANTS.VALUE FROM CONSTANTS";
        Session session = sessionFactory.openSession();
        List constants = session.createSQLQuery(SELECT_SQL).list();
        session.close();
        return constants;
    }

    public String getConstantValueDB(String key) {
        Session session = sessionFactory.openSession();
        String value = session.get(Constants.class, key).getValue();
        session.close();
        return value;
    }

    public List selectSessionsFromBD(String mode, String order) {
        final String SELECT_SQL = "" +
                "select * from (select distinct sessions.id, sessions.ip,sessions.timestart,sessions.timeend, 'false' as operation from SESSIONS left join history on SESSIONS.id = HISTORY.id where operation is null\n" +
                "union all\n" +
                "select distinct sessions.id, sessions.ip,sessions.timestart,sessions.timeend, 'true' as operation from SESSIONS left join history on SESSIONS.id = HISTORY.id where operation is not null) order by "+mode+" "+order;

        Session session = sessionFactory.openSession();
        List dbRows = session.createSQLQuery(SELECT_SQL).list();
        session.close();
        return dbRows;
    }

    public List selectDataFromBD(String mode, String order, String id) {
        final String SELECT_SQL = "SELECT OPERATION, FIRSTOPERAND, SECONDOPERAND, ANSWER, TIME FROM HISTORY where '"+id+"'=ID ORDER BY "+mode+" "+order;
        Session session = sessionFactory.openSession();
        List dbRows = session.createSQLQuery(SELECT_SQL).list();
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
