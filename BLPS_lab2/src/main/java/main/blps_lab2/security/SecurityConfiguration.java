package main.blps_lab2.security;

import lombok.RequiredArgsConstructor;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.security.jwt.AuthorizationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AbstractJaasAuthenticationProvider jaasAuthenticationProvider;
    private final AuthorizationTokenFilter authorizationTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(jaasAuthenticationProvider)
                .addFilterAfter(authorizationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/admin/**").hasRole(RoleEnum.ADMIN.getAuthority())
                                .requestMatchers("/client/**").hasAnyRole(RoleEnum.CLIENT.getAuthority(), RoleEnum.ADMIN.getAuthority())
                                .requestMatchers("/public/**").permitAll()
                                .anyRequest().permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MainPasswordEncoder();
    }

    @Bean
    public AuthorizationTokenFilter authenticationJwtTokenFilter() {
        return authorizationTokenFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
