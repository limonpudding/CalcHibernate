package app.pages.logic;

import app.database.JDBC;
import app.database.entities.Users;
import app.security.UserDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Service("getReg")
public class Reg extends Page {

    private final JDBC jdbc;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsDao userDetailsDao;

    @Autowired
    public Reg(JDBC jdbc, PasswordEncoder passwordEncoder, UserDetailsDao userDetailsDao) {
        this.jdbc = jdbc;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsDao = userDetailsDao;
    }

    @Override
    @Transactional
    public ModelAndView build(Map params) {
        ModelAndView mav;
        Users user = new Users();
        if (!params.get("rpassword").equals(params.get("password"))) {
            mav = new ModelAndView("register");
            mav.addObject("error", "Пароли не совпадают");
        } else {
            user.setUsername((String) params.get("username"));
            user.setPassword(passwordEncoder.encode((String) params.get("password")));
            if (userDetailsDao.findUserByUsername(user.getUsername()) == null) {
                jdbc.putUserInDB(user);
                mav = new ModelAndView("login");
                mav.addObject("message", "Вы успешно зарегистрировались! Пожалуйста, войдите под своей учётной записью");
            } else {
                mav = new ModelAndView("register");
                mav.addObject("error", "Пользователь с таким именем уже существует");
            }
        }
        return mav;
    }
}
