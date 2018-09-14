package app.pages.logic;

import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

public abstract class Page {

    public ModelAndView build() throws Exception {
        return null;
    }

    public ModelAndView build(Map params) throws Exception {
        return null;
    }
}
