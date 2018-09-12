package app.pages.logic;

import app.database.JDBC;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service("getAccountsManager")
public class AccountsManager extends Page {

    @Autowired
    JDBC jdbc;
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("accountsManager");
        mav.addObject("users",jdbc.selectUsersFromBD());
        return mav;
    }
}