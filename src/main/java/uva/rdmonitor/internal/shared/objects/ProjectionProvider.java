package uva.rdmonitor.internal.shared.objects;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import com.querydsl.core.types.Expression;
import uva.rdmonitor.internal.shared.interfaces.ProjectionJoinExpression;

import java.util.List;

@Builder
@Getter
public class ProjectionProvider<T> {

    @NotNull
    private final List<Expression<?>> projections;
    private final List<ProjectionJoinExpression> joins;
    private final Class<T> resultClass;
}
