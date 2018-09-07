package app.pages.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


@Service("getRegistration")
public class Registration extends Page {
    @Override
    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("register");
        return mav;
    }
}
