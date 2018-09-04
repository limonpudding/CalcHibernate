package app.pages.logic;

import app.database.JDBC;
import app.database.entities.Roles;
import app.database.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service("getReg")
public class Reg extends Page {

    @Autowired
    JDBC jdbc;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ModelAndView build() {
        ModelAndView mav;
        Users user = new Users();
        if (!params.get("rpassword").equals(params.get("password"))) {
            mav = new ModelAndView("register");
            mav.addObject("error", "Пароли не совпадают");
        } else {
            user.setUsername((String) params.get("username"));
            user.setPassword(passwordEncoder.encode((String) params.get("password")));
            user.setRole(Roles.ROLE_USER);
            jdbc.putUserInDB(user);
            mav = new ModelAndView("home");
            mav.addObject("message", "Вы успешно зарегистрировались!");
        }
        return mav;
    }
}
