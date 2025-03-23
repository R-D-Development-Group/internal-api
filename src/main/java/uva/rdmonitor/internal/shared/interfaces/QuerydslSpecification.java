package uva.rdmonitor.internal.shared.interfaces;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import uva.rdmonitor.internal.shared.utils.SpecificationUtils;

import java.util.List;



public interface QuerydslSpecification<T> {

    List<Predicate> build(JPQLQuery<T> jpqlQuery);

    default Predicate[] buildArray(JPQLQuery<?> jpqlQuery) {
        return build((JPQLQuery<T>) jpqlQuery).toArray(new Predicate[0]);
    }

    default QuerydslSpecification<T> and(QuerydslSpecification<T> spec) {
        return jpqlQuery -> SpecificationUtils.joinPredicates(build(jpqlQuery), spec.build(jpqlQuery), true);
    }

    default QuerydslSpecification<T> or(QuerydslSpecification<T> spec) {
        return jpqlQuery -> SpecificationUtils.joinPredicates(build(jpqlQuery), spec.build(jpqlQuery), false);
    }
}

