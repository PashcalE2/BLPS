package main.blps_lab2.security.jaas;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.repository.XMLUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JaasConfig {
    private final UserRepository xmlUserRepository;

    @Bean
    public InMemoryConfiguration configuration() {
        log.info("JAAS: вызвана конфигурация");

        AppConfigurationEntry configEntry = new AppConfigurationEntry(JaasLoginModule.class.getName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                Map.of("userRepository", xmlUserRepository));

        AppConfigurationEntry[] configurationEntries = new AppConfigurationEntry[] { configEntry };
        return new InMemoryConfiguration(Map.of("SPRINGSECURITY", configurationEntries));
    }

    @Bean
    public AbstractJaasAuthenticationProvider jaasAuthenticationProvider(javax.security.auth.login.Configuration configuration) {
        log.info("JAAS: jaasAuthProvider СОЗДАН");

        DefaultJaasAuthenticationProvider provider = new DefaultJaasAuthenticationProvider();
        provider.setConfiguration(configuration);
        provider.setAuthorityGranters(new AuthorityGranter[] { new JaasAuthorityGranter(xmlUserRepository) });
        return provider;
    }
}
