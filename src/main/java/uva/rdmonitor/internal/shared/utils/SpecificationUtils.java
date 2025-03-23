package uva.rdmonitor.internal.shared.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.jpa.JPQLQuery;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@UtilityClass
public final class SpecificationUtils {
    public <T> void join(JPQLQuery<T> jpqlQuery, EntityPath<?> target, JoinAction joinAction) {
        boolean exists = jpqlQuery.getMetadata().getJoins().stream().anyMatch(joinExpression -> joinExpression.getTarget().equals(target));
        if (!exists) {
            joinAction.join();
        }
    }

    public <T> void join(JPQLQuery<T> jpqlQuery, ListPath<?, ?> target, JoinAction joinAction) {
        boolean exists = jpqlQuery.getMetadata().getJoins().stream()
                .filter(joinExpression -> joinExpression.getTarget() instanceof Operation)
                .map(joinExpression -> (Operation<?>) joinExpression.getTarget())
                .anyMatch(operation -> operation.getArgs().contains(target));
        if (!exists) {
            joinAction.join();
        }
    }
    public List<Predicate> joinPredicates(List<Predicate> predicates, List<Predicate> predicatesToAdd, boolean andJoin) {
        Predicate predicate = joinPredicates(predicatesToAdd, andJoin);
        if (predicate != null) {
            predicates = new ArrayList<>(predicates);
            predicates.add(predicate);
        }
        return predicates;
    }

    public <P extends Predicate> Predicate joinPredicates(List<P> predicates, boolean andJoin) {
        if (CollectionUtils.isEmpty(predicates)) {
            return null;
        }

        if (predicates.size() == 1) {
            return predicates.getFirst();
        }

        BooleanBuilder builder = new BooleanBuilder();

        predicates.forEach(andJoin ? builder::and : builder::or);
        return builder;
    }

    @FunctionalInterface
    public interface JoinAction {
        void join();
    }
}
