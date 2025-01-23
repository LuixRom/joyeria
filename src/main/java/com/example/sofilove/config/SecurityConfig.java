package com.example.sofilove.config;

import com.example.sofilove.Usuario.domain.UsuarioDetailsServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UsuarioDetailsServiceImpl userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173")); // Origen del frontend
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
                    corsConfiguration.setAllowedHeaders(List.of("*")); // Permitir todos los encabezados
                    corsConfiguration.setAllowCredentials(true); // Permitir credenciales (cookies, headers de autenticación, etc.)
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(authorize -> authorize
                        // Rutas públicas
                        .requestMatchers("/auth/**").permitAll()

                        // Acceso de carrito
                        .requestMatchers(HttpMethod.GET, "/carrito/me").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/carrito/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/carrito/{carritoId}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/carrito/**").hasAuthority("ADMIN")

                        // Acceso de carritoItem
                        .requestMatchers(HttpMethod.POST, "/cart-items/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/cart-items/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/cart-items/**").hasAuthority("CLIENTE")

                        // Acceso de category
                        .requestMatchers(HttpMethod.GET, "/category/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/category/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/category/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/category/**").hasAuthority("ADMIN")

                        // Acceso de pedido
                        .requestMatchers(HttpMethod.POST, "/pedido/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/pedido/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pedido/**").hasAuthority("ADMIN")

                        // Acceso de pedidoItem
                        .requestMatchers(HttpMethod.GET, "/pedidoItem/**").hasAuthority("ADMIN")

                        // Acceso de producto
                        .requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/product/**").hasAuthority("ADMIN")

                        // Acceso de review
                        .requestMatchers(HttpMethod.POST, "/review/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/review/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/review/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("CLIENTE")


                        // Acceso de usuario
                        .requestMatchers(HttpMethod.GET, "/user/me").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/user/{userId}").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/user/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/create-admin").hasAuthority("ADMIN")



                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userService.userDetailsService());
        return authProvider;
    }


    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ADMIN > CLIENTE");

        return hierarchy;
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        expressionHandler.setDefaultRolePrefix("");

        return expressionHandler;
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
