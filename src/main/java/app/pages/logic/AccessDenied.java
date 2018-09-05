package app.pages.logic;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service("getAccessDenied")
public class AccessDenied extends Page {
    @Override
    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("accessDenied");
        return mav;
    }
}
