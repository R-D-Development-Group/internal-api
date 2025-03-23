package uva.rdmonitor.internal.authentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import uva.rdmonitor.internal.authentication.appUser.repository.AppUserRepository;
import uva.rdmonitor.internal.authentication.component.StaticAdminAuditorAware;
import uva.rdmonitor.internal.authentication.appUser.entity.AppUserEntity;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {

    @Bean
    public AuditorAware<AppUserEntity> auditorProvider(AppUserRepository appUserRepository) {
        return new StaticAdminAuditorAware(appUserRepository);
    }
}
