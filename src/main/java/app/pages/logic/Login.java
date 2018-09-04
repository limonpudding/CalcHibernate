package app.pages.logic;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service("getLogin")
public class Login extends Page {
    @Override
    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }
}
