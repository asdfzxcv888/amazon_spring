package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity (enable it in production with proper config)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/adduser", "/user","/login").permitAll() // Allow these endpoints without authentication
                .anyRequest().authenticated() // Require authentication for all other requests
            )
            .httpBasic(); // Enable basic authentication (optional)
        return http.build();
    }
}



// package com.example.demo;


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// public class SecurityConfig {

//     @Autowired
//     private JwtFilter jwtFilter;

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//         return configuration.getAuthenticationManager();
//     }

//     @Bean
//     public org.springframework.security.web.SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http.csrf().disable()
//                 .authorizeRequests()
//                 .requestMatchers("/login", "/register").permitAll() // Public endpoints
//                 .anyRequest().authenticated() // Protect all other endpoints
//                 .and()
//                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//         http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }
// }
