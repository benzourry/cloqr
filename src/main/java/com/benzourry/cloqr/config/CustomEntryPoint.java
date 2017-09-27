package com.benzourry.cloqr.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MohdRazif on 3/17/2015.
 */
public class CustomEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    public CustomEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
//        System.out.println("X-REQUESTED-WITH:"+request.getHeader(X_REQUESTED_WITH));
        if (XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH))) {
//            System.out.println("UNAUTHORIZE IS AJAX");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
//            System.out.println("UNAUTHORIZE IS NOT AJAX");
            super.commence(request, response, exception);
        }
    }

}