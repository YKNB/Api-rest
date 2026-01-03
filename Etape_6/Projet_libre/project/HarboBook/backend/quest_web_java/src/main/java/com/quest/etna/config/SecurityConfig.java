package com.quest.etna.config;
import com.quest.etna.config.JwtAuthenticationEntryPoint;
import com.quest.etna.config.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public JwtRequestFilter authenticationJwtTokenFilter;

    @Autowired
    private DaoAuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/authenticate/**").permitAll()
                                //.requestMatchers("/address/**").permitAll()
                                .requestMatchers("/me/**").permitAll()
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers("/address/**").permitAll()
                                .requestMatchers("/book/**").permitAll()
                                .requestMatchers("/books/**").permitAll()
                                //.requestMatchers("/me").hasAuthority("ROLE_USER")
                                //.requestMatchers(HttpMethod.GET,"/me").hasRole("USER")
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider);

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200"); // Remplacez avec l'URL de votre application Angular
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}