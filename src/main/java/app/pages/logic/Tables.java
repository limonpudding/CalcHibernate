package app.pages.logic;

import app.database.JDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Service("getTables")
public class Tables extends Page {

    @Autowired
    JDBC jdbc;

    @Override
    public ModelAndView build(Map params) throws Exception {
        String modeSort = (String) params.get("mode");
        String orderSort = (String) params.get("order");
        String id = (String) params.get("id");
        ModelAndView mav;
        if ("1".equals(params.get("table"))) {
            mav = new ModelAndView("createTableSessions");
            mav.addObject("fullSessionsHistory", jdbc.selectSessionsFromBD(modeSort, orderSort));
        } else {
            mav = new ModelAndView("createTableOperations");
            mav.addObject("operationsHistory", jdbc.selectDataFromBD(modeSort, orderSort, id));
        }
        return mav;
    }

}

