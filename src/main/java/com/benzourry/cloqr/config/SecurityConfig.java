package com.benzourry.cloqr.config;

import com.benzourry.cloqr.core.helper.security.CustomAuthenticationFailureHandler;
import com.benzourry.cloqr.core.helper.security.UserDetailsContextMapperImpl;
import com.benzourry.cloqr.core.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by MohdRazif on 4/10/2015.
 */

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebSecurity
public class SecurityConfig {


    private static final String ACCESS_DENIED_JSON = "{\"message\":\"You are not privileged to request this resource.\", \"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\"}";
    private static final String UNAUTHORIZED_JSON = "{\"message\":\"Full authentication is required to access this resource.\", \"access-denied\":true,\"cause\":\"NOT AUTHENTICATED\"}";


    @Autowired
    private Environment env;

    @Value("${ldap.contextSource.domain}")
    private String DOMAIN;

    @Value("${ldap.contextSource.url}")
    private String URL;
//    @Bean
//    protected AccountDetailsService userDetailsService() {
//        return new AccountDetailsService();
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //AD
        auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
        auth.authenticationEventPublisher(defaultAuthenticationEventPublisher());
//        auth
//                .userDetailsService(userDetailsService());
        //In-mem
        //auth.inMemoryAuthentication()
        //        .withUser("blmrazif").password("razen6").authorities("ROLE_ADMIN");


    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder;
//    }

    @EnableOAuth2Sso
    @Configuration
//    @Order(4)
    public static class SecurityConfigOAuth extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
//                .requestMatchers().antMatchers("/**")
//                    .and()
                    .authorizeRequests()
                    .antMatchers("/login", "/logout", "/o/**").permitAll()
                    .antMatchers("/_assets/**").permitAll()
                    .antMatchers("/_vendor/**").permitAll()
                    .antMatchers("/api/public/**").permitAll()
                    .antMatchers("/public/**").permitAll()
                    .antMatchers("/api/v*/public/**").permitAll()
                    .antMatchers("/api/event/**/**/key").permitAll()
                    .antMatchers("/api/lookup/**/list").permitAll()
                    .antMatchers("/api/v*/lookup/**/list").permitAll()
                    .anyRequest().authenticated() //will try to authenticate against OAuth session, not application session!
//                    .and()
//                    .exceptionHandling().authenticationEntryPoint(new CustomEntryPoint("/login"))
                    .and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll()
                    .logoutSuccessUrl("/o/logout");
        }
    }

    /**
     * Enable this system as a resource server.
     * There's no easy way to enable both resource and client
     * To request the API using token, request through iris7/rest/api/**
     */
    @Configuration
    @EnableResourceServer
//    @Order(1)
    public static class SecurityConfigOAuthResource extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .requestMatchers().antMatchers("/api/**")

                .and()
                .authorizeRequests()
                    .antMatchers("/api/event/**/**/key").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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


    @Bean
    CustomAuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
    }

    @Bean
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(DOMAIN, URL);
        provider.setUseAuthenticationRequestCredentials(true);
        provider.setConvertSubErrorCodesToExceptions(true);
        provider.setUserDetailsContextMapper(userDetailsContextMapper());

        return provider;
    }

    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        UserDetailsContextMapper contextMapper = new UserDetailsContextMapperImpl();
        return contextMapper;
    }

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}

