package app.pages.logic;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service("getAccountsManager")
public class AccountsManager extends Page {
    @Override
    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("accountsManager");
        return mav;
    }
}