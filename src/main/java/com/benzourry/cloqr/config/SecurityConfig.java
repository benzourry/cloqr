package com.benzourry.cloqr.config;

import com.benzourry.cloqr.core.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by MohdRazif on 4/10/2015.
 */

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebSecurity
public class SecurityConfig {


    private static final String ACCESS_DENIED_JSON = "{\"message\":\"You are not privileged to request this resource.\", \"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\"}";
    private static final String UNAUTHORIZED_JSON = "{\"message\":\"Full authentication is required to access this resource.\", \"access-denied\":true,\"cause\":\"NOT AUTHENTICATED\"}";

    @Bean
    protected AccountDetailsService userDetailsService() {
        return new AccountDetailsService();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //AD
//        auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
//        auth.authenticationEventPublisher(defaultAuthenticationEventPublisher());
        auth
                .userDetailsService(userDetailsService());
        //In-mem
        //auth.inMemoryAuthentication()
        //        .withUser("blmrazif").password("razen6").authorities("ROLE_ADMIN");


    }

        @Bean
        public PasswordEncoder passwordEncoder() {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder;
        }

    @Configuration
    @Order(2)
    public class FormSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

//        @Bean
//        @Override
//        protected AccountDetailsService userDetailsService() {
//            return new AccountDetailsService();
//        }
//
//        @Autowired
//        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            auth
//                    .userDetailsService(userDetailsService()); //.passwordEncoder(passwordEncoder());
//        }

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http
                    .formLogin() //.and()
                    .loginPage("/login")
                    .permitAll()
                    //.failureHandler(authenticationFailureHandler())
                    .and().logout()

                    .and().authorizeRequests()
                    .antMatchers("/_assets/**").permitAll()
                    .antMatchers("/_vendor/**").permitAll()
                    .antMatchers("/api/public/**").permitAll()
                    .antMatchers("/api/v1/lookup/**/list").permitAll()
                    .antMatchers("/api/v1/**").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/login").permitAll()
                    // .antMatchers("/","/login").permitAll()
                    .anyRequest().authenticated()

                    .and().exceptionHandling().authenticationEntryPoint(new CustomEntryPoint("/login"))

//                .and().rememberMe()
                    .and().csrf().disable();



//            http
//                    .sessionManagement() //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    //.httpBasic().realmName("IRIS7").and()
//                    .formLogin() //.and()
//                    .loginPage("/login")
//                    .permitAll()
//                    .and().logout()
//                    .and().authorizeRequests()
//                    .antMatchers("/_assets/**").permitAll()
//                    .antMatchers("/_vendor/**").permitAll()
//                    .antMatchers("/api/**").permitAll()
//                    .antMatchers("/signup").permitAll()
//                    .antMatchers("/login").permitAll()
//                    .antMatchers("/perform-register").permitAll()
//                    // .antMatchers("/","/login").permitAll()
//                    .anyRequest().authenticated()
//                    .and().exceptionHandling().authenticationEntryPoint(new CustomEntryPoint("/login"))
//                    .and().rememberMe()
//                    //.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
//                    //.csrfTokenRepository(csrfTokenRepository());
//                    .and().csrf().disable();

        }


    }

    @Configuration
    @Order(1)
    //MUST 2 because Oauth ResourceServerConfigurer is default on 3 in filter-> if not htt basic checking will comes later
    public static class HttpBasicRestSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
        //http basic
//        @Bean
//        @Override
//        protected AccountDetailsService userDetailsService() {
//            return new AccountDetailsService();
//        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatcher(new BasicRequestMatcher()) //detect if requset using basic auth
                    .httpBasic().realmName("MY API")
                    .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()

                    .exceptionHandling()
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())


                    .and().authorizeRequests()
                    .antMatchers("/_assets/**").permitAll()
                    .antMatchers("/_vendor/**").permitAll()
                    .antMatchers("/api/public/**").permitAll()
                    .antMatchers("/api/v1/lookup/**/list").permitAll()
                    // .antMatchers("/","/login").permitAll()
                    .anyRequest().authenticated();
//                    .and().userDetailsService(userDetailsService());

        }
    }


    private static class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.print(ACCESS_DENIED_JSON);
            out.flush();
            out.close();

        }
    }

    private static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = response.getWriter();
            out.print(UNAUTHORIZED_JSON);
//            out.print(UNAUTHORIZED_JSON);
            out.flush();
            out.close();
        }
    }


    private static class BasicRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            return (auth != null && auth.startsWith("Basic"));
        }
    }


}
