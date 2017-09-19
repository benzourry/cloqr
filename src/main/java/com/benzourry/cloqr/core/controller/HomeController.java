package com.benzourry.cloqr.core.controller;

import com.benzourry.cloqr.core.dao.AccountRepository;
import com.benzourry.cloqr.core.dao.AccountRoleRepository;
import com.benzourry.cloqr.core.model.Account;
import com.benzourry.cloqr.core.model.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MohdRazif on 4/10/2015.
 */
@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountRoleRepository accountRoleRepository;

    @RequestMapping(value = "login")
    public String printLogin() {
        return "login";
    }

    @RequestMapping(value="signup")
    public String register(Principal principal, ModelMap model, HttpServletRequest request){
        return "signup";
    }

    @RequestMapping(value="signup", method = RequestMethod.POST)
    public String performRegister(Principal principal, Account account, ModelMap model){

        Account newAccount = accountRepository.save(account);
        model.put("account", newAccount);

        return "redirect:/";
    }

    @RequestMapping(value = "/")
    public String printHome(Principal principal, ModelMap model, HttpServletRequest request, HttpSession session) {
        //   System.out.println(principal.getName());
        String redirect = "home";

        String username = principal.getName();
        Map<String, Object> data = new HashMap<>();
        Account account = accountRepository.findByUsername(username); //.findPersonLdapByUsername(username);

        data.put("account", account);


        Account a = accountRepository.findByUsername(username);

        if (a == null) {
            /* If the user dont have their role, means this is their first time login */
            List<AccountRole> roleList = accountRoleRepository.findAll();//.findUsersFeaturesByEnabled(Constant.ACTIVE_FLAG);
            model.addAttribute("roleList", roleList);
            redirect = "register";
        } else {
            // if User is not enabled, assign them the researcher (public) role.
//            if (a.getStatus() == 0) {
//                UsersFeatures af = usersService.getUsersFeatures("ROLE_RSCHER");
//                a.setUsersFeatures(af);
//            }
//            System.out.println("USER_F:" + a.getUsersFeatures());
            data.put("role", a.getAccountRole());
          //  data.put("features", a.getUsersFeatures().getFeaturesList());
            // model.addAttribute("users", a);
            redirect = "home";
        }

        model.addAttribute("activeProfile", data);

        // }
        return redirect;
    }


}
