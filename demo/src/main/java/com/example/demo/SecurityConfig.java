// // package com.example.demo;

// // import org.springframework.context.annotation.Bean;
// // import org.springframework.context.annotation.Configuration;
// // import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// // import org.springframework.security.web.SecurityFilterChain;

// // @Configuration
// // public class SecurityConfig {

// //     @Bean
// //     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// //         http
// //             .csrf().disable() // Disable CSRF for simplicity (enable it in production with proper config)
// //             .authorizeHttpRequests(authorize -> authorize
// //                 .requestMatchers("/adduser", "/user","/login").permitAll() // Allow these endpoints without authentication
// //                 .anyRequest().authenticated() // Require authentication for all other requests
// //             )
// //             .httpBasic(); // Enable basic authentication (optional)
// //         return http.build();
// //     }
// // }



// // // package com.example.demo;


// // // import org.springframework.beans.factory.annotation.Autowired;
// // // import org.springframework.context.annotation.Bean;
// // // import org.springframework.context.annotation.Configuration;
// // // import org.springframework.security.authentication.AuthenticationManager;
// // // import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// // // import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// // // import org.springframework.security.config.http.SessionCreationPolicy;
// // // import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// // // @Configuration
// // // public class SecurityConfig {

// // //     @Autowired
// // //     private JwtFilter jwtFilter;

// // //     @Bean
// // //     public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
// // //         return configuration.getAuthenticationManager();
// // //     }

// // //     @Bean
// // //     public org.springframework.security.web.SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// // //         http.csrf().disable()
// // //                 .authorizeRequests()
// // //                 .requestMatchers("/login", "/register").permitAll() // Public endpoints
// // //                 .anyRequest().authenticated() // Protect all other endpoints
// // //                 .and()
// // //                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

// // //         http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

// // //         return http.build();
// // //     }
// // // }


// package com.example.demo;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// public class SecurityConfig {

//     private final JwtFilter jwtFilter;

//     public SecurityConfig(JwtFilter jwtFilter) {
//         this.jwtFilter = jwtFilter;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http.csrf().disable()
//                 .authorizeRequests()
//                 .requestMatchers("/login").permitAll() // Exclude /login from JWT filter
//                 .anyRequest().authenticated() // Require authentication for all other requests
//                 .and()
//                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//         // Add the JwtFilter before UsernamePasswordAuthenticationFilter
//         http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }
// }



package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JwtFilter jwtFilter, 
                          CustomAuthenticationEntryPoint authenticationEntryPoint,
                          CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/login","/adduser").permitAll() // Allow access to /login without authentication
                .anyRequest().authenticated() // Protect all other endpoints
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) // Handle unauthenticated access
                .accessDeniedHandler(accessDeniedHandler) // Handle unauthorized access
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add the JwtFilter
        http.addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

