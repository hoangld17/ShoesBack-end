package com.example.shoesmanagement.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity.cors().and()
              // we don't need CSRF because our token is invulnerable
              .csrf().disable()
              // don't create session
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
              .authorizeRequests()
              // Allow access public resource
              .antMatchers(
                      HttpMethod.GET,
                      "/",
                      "/favicon.ico",
                      "/**/*.html",
                      "/**/*.css",
                      "/**/*.js",
                      "/**/*.png",
                      "/**/*.gif",
                      "/public/**",
                      "/**/*.json",
                      "/**/*.jpg",
                      // enable swagger endpoints
                      "/swagger-resources/**",
                      "/configuration/ui",
                      "/configuration/security",
                      "/manage/api-docs"
              ).permitAll()
              // allow CORS option calls
              .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
              .antMatchers("/consumer/signin").permitAll()
              .antMatchers("/swagger-ui/**").permitAll()//
              .antMatchers("/file/imageBeverage/**").permitAll()//
              .antMatchers("/file/imageEmployee/**").permitAll()//
              .antMatchers("/h2-console/**/**").permitAll()
              // Disallow everything else..
              .anyRequest().authenticated();// All other request must be specify token

      // Custom JWT based security filter
      httpSecurity.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

      // disable page caching
      httpSecurity.headers().cacheControl();
      httpSecurity.headers().frameOptions().disable();
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//
//    // Disable CSRF (cross site request forgery)
//    http.csrf().disable();
//
//    // No session will be created or used by spring security
//    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//    // Entry points
//    http.authorizeRequests()//
//        .antMatchers("/users/signin").permitAll()//
//        .antMatchers("/file/imageBeverage/**").permitAll()//
//        .antMatchers("/file/imageEmployee/**").permitAll()//
//        .antMatchers("/h2-console/**/**").permitAll()
//        // Disallow everything else..
//        .anyRequest().authenticated();
//
//    // If a user try to access a resource without having enough permissions
//    http
//    .exceptionHandling()
//    .authenticationEntryPoint((request, response, e) -> 
//    {
//    	ShowDataResponse<String> as = new ShowDataResponse<String>(HttpStatus.FORBIDDEN.value(), "Access denied! Please login!");
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.getWriter().write(as.toString());
//    });
//
//    // Apply JWT
//    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
//
//    // Optional, if you want to test the API from a browser
//    // http.httpBasic();
//    http.cors();
//  }
//
  @Override
  public void configure(WebSecurity web) throws Exception {
    // Allow swagger to be accessed without authentication
    web.ignoring().antMatchers("/v2/api-docs")//
        .antMatchers("/swagger-resources/**")//
        .antMatchers("/swagger-ui.html")//
        .antMatchers("/configuration/**")//
        .antMatchers("/webjars/**")//
        .antMatchers("/public")
        
        // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
        .and()
        .ignoring()
        .antMatchers("/h2-console/**/**");;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
