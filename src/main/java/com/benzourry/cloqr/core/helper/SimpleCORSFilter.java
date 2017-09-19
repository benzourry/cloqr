/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benzourry.cloqr.core.helper;

/**
 *
 * @author MohdRazif
*/

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter {

        @Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
                HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		//response.setHeader("Access-Control-Expose-Headers", "Accept, Origin, X-Requested-With, Content-Type, Last-Modified, Authorization");
		response.setHeader("Access-Control-Allow-Headers", "Accept, Origin, X-Requested-With, Content-Type, Last-Modified, Authorization");
             //   response.setHeader("Access-Control-Allow-Credentials", "true");
            //    chain.doFilter(req, res);
            if (request.getMethod().equals("OPTIONS")) {
                try {
                    response.setStatus(HttpServletResponse.SC_OK);
                   // response.getWriter().print("OK");
                    //response.getWriter().flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                chain.doFilter(request, response);
            }
        }

        @Override
	public void init(FilterConfig filterConfig) {}

        @Override
	public void destroy() {}
        

} 