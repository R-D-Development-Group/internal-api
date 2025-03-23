package uva.rdmonitor.internal.authentication.appUser.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uva.rdmonitor.internal.shared.entity.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "APP_USER")
@AllArgsConstructor
@NoArgsConstructor
public class AppUserEntity extends BaseEntity {

    private String email;
    private String fullName;
}
