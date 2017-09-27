package com.benzourry.cloqr.core.helper.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryAuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by STLING on 5/21/2015.
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
//    @Autowired
//    private AuditLogService auditLogService;

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ActiveDirectoryAuthenticationException adException = (ActiveDirectoryAuthenticationException) exception.getCause();

        String username = request.getParameter("username");
        switch (adException.getDataCode()){
            case "52e":  //invalid credential
                //auditLogService.save(username,"52e", "INVALID CREDENTIAL");
//               response.sendRedirect("/login?error=52e");
                break;
            case "532":  //password expired
                //auditLogService.save(username,"532", "PASSWORD EXPIRED");
//               response.sendRedirect("/login?error=532");
                break;
            case "533":  //account disabled
                //auditLogService.save(username, "533", "ACCOUNT DISABLED");
//               response.sendRedirect("/login?error=553");
                break;
            case "701":  //account expired
                // auditLogService.save(username,"701", "ACCOUNT EXPIRED");
//               response.sendRedirect("/login?error=701");
                break;
            case "773":  //user need reset password
                //auditLogService.save(username,"773", "FORCE RESET PASSWORD");
//               response.sendRedirect("/changepwd?username="+username);
                break;
            case "775":  //account locked
                // auditLogService.save(username,"775", "ACCOUNT LOCKED");
//               response.sendRedirect("/login?error=775");
                break;
        }

        if (XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}