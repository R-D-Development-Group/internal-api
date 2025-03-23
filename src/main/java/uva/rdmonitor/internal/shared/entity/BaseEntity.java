package uva.rdmonitor.internal.shared.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uva.rdmonitor.internal.authentication.appUser.entity.AppUserEntity;
import uva.rdmonitor.internal.shared.interfaces.GenericDtoProvider;
import uva.rdmonitor.internal.shared.interfaces.VersionableObject;

import java.time.LocalDateTime;

@FieldNameConstants
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements GenericDtoProvider, VersionableObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime lastModified;

    @Version
    private Long version;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private AppUserEntity createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_MODIFIED_BY")
    private AppUserEntity lastModifiedBy;

    @Override
    public String toString() {
        return String.format("%s [id=%d]", getClass().getSimpleName(), getId());
    }
}
