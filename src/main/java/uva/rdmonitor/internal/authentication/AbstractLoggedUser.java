package uva.rdmonitor.internal.authentication;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@SuperBuilder
public abstract class AbstractLoggedUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -8314430297646002419L;

    @NonNull
    private final Long appUserId;

    @NonNull
    private final String firstName;

    @NonNull
    private final String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
