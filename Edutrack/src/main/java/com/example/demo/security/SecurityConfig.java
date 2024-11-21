package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.services.impl.UserServiceImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    @Autowired
    private JwtFilter jwtFilter;

    private UserServiceImpl myUserDetailService;
    
    
    @Autowired
    public void setMyUserDetailService(UserServiceImpl myUserDetailService) {
    this.myUserDetailService=myUserDetailService;
    }
    
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
            	.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> 
                exceptionHandling.accessDeniedPage("/error/403")
            );

        return http.build();
    }


    @Bean
    @Order(2)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(c -> c.disable())
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/", "/index", "/login",  "/css/**", "/images/**", "/error/**").permitAll()  // acceso público a login, register y recursos estáticos
                // .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()  // Requiere autenticación para las demás rutas
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.successHandler(customAuthenticationSuccessHandler())
				.permitAll()
			)
			.logout((logout) -> logout
                .logoutUrl("/logout") 
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")// Permitir GET
                .logoutSuccessUrl("/login") 
                .logoutSuccessHandler(customLogoutSuccessHandler())
                .permitAll()
			)
			.exceptionHandling(exceptionHandling -> 
                exceptionHandling.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login") {
                    @Override
                    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
                        String loginUrl = super.determineUrlToUseForThisRequest(request, response, exception);
                        return buildUrlWithForwardedHeaders(request, loginUrl);
                    }
                })
            )
            .exceptionHandling(exceptionHandling -> 
                exceptionHandling.accessDeniedPage("/error/403")
            );
			// .requiresChannel()  // Requiere HTTPS para todas las rutas
			// .anyRequest().requiresSecure();  // Requiere que las peticiones sean hechas sobre HTTPS

		return http.build();
	}


    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String targetUrl = buildUrlWithForwardedHeaders(request, "/");
            response.sendRedirect(targetUrl);
        };
    }

    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler() {
        return (request, response, authentication) -> {
            // Construye la URL con los encabezados x-forwarded-* y redirige al usuario tras el logout
            String targetUrl = buildUrlWithForwardedHeaders(request, "/login?logout");
            response.sendRedirect(targetUrl);
        };
    }

    private String buildUrlWithForwardedHeaders(HttpServletRequest request, String path) {
        String schema = request.getHeader("x-forwarded-proto");
        String host = request.getHeader("x-forwarded-host");
        if (schema != null && host != null) {
            return schema + "://" + host + path;
        }
        return path;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
    
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailService); // Usar el servicio personalizado
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false); // Mantiene las credenciales después de la autenticación

        return providerManager;
    }


}