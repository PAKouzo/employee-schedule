package vju.finalexam.project.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

import vju.finalexam.project.model.Event;

public class EventSpecifications {
    public static Specification<Event> recurring() {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get("recurring"), true);
        };
    }
    
    private static Predicate betweenDatesPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb, ZonedDateTime start, ZonedDateTime end) {
            Predicate res = cb.or(              
                    cb.and(
                            cb.equal(root.get("recurring"), true),
                            cb.lessThanOrEqualTo(root.get("dtStart"), end),
                            cb.equal(cb.<Boolean>selectCase().when(
                                    cb.isNotNull(root.get("untilDate")), 
                                    cb.greaterThanOrEqualTo(root.get("untilDate"), start))
                                    .otherwise(cb.literal(true)), cb.literal(true))
                    ),
                    cb.and(
                            cb.not(cb.coalesce(root.get("recurring"), false)),
                            cb.greaterThanOrEqualTo(root.get("dtEnd"), start),
                            cb.lessThanOrEqualTo(root.get("dtEnd"), end)
                    ),
                    cb.and(
                            cb.not(cb.coalesce(root.get("recurring"), false)),
                            cb.greaterThanOrEqualTo(root.get("dtStart"), start),
                            cb.lessThanOrEqualTo(root.get("dtStart"), end)
                    ),
                    cb.and(
                            cb.not(cb.coalesce(root.get("recurring"), false)),
                            cb.lessThanOrEqualTo(root.get("dtStart"), start),
                            cb.greaterThanOrEqualTo(root.get("dtEnd"), end)
                    )
            );
            
            return res;
    }
    
    public static Specification<Event> betweenDates(ZonedDateTime start, ZonedDateTime end) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return betweenDatesPredicate(root, query, cb, start, end);
        };
    }
    
    public static Specification<Event> betweenDatesAndGroups(ZonedDateTime start, ZonedDateTime end, List<Long> groups) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate res = betweenDatesPredicate(root, query, cb, start, end);
            
            if (groups != null)
                res = cb.and(res,
                    root.get("employeeFk").in(groups)
                );
            
            return res;
        };
    }
}
