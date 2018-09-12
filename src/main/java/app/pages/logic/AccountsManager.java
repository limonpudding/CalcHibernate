package app.pages.logic;

import app.database.JDBC;
import app.database.entities.Sessions;
import app.database.entities.Users;
import app.database.entities.dto.UsersDto;
import app.database.entities.dto.UsersMapper;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.LinkedList;
import java.util.List;

@Service("getAccountsManager")
public class AccountsManager extends Page {

    @Autowired
    JDBC jdbc;
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public ModelAndView build() {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Sessions> criteria = builder.createQuery(Sessions.class);
        criteria.from(Sessions.class);
        List<Sessions> sessions = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
        ModelAndView mav = new ModelAndView("accountsManager");
        mav.addObject("users",jdbc.selectUsersFromBD());
        return mav;
    }
}