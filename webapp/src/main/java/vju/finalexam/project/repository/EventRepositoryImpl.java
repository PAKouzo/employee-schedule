package vju.finalexam.project.repository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static vju.finalexam.project.repository.specification.EventSpecifications.betweenDatesAndGroups;
import static vju.finalexam.project.util.DateUtils.formatZDT;
import static vju.finalexam.project.util.EntityUtils.entityToMap;
import static vju.finalexam.project.util.EventUtils.eventToVevent;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.component.VEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import vju.finalexam.project.model.Employee;
import vju.finalexam.project.model.Event;

@Component
public class EventRepositoryImpl {
    private static final Logger logger = LoggerFactory.getLogger(EventRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
    private JpaSpecificationExecutor<Event> executor;

    @PostConstruct
    public void init() {
        executor = new SimpleJpaRepository<Event, String>(Event.class, entityManager);
    }

    public List<Map> eventsInRange(ZonedDateTime start, ZonedDateTime end, List<Long> groups, boolean detailed) {
        List<Event> events = executor.findAll(betweenDatesAndGroups(start, end, groups));
        
        List<Map> records = events.stream().map(e -> this.entityMapped(e)).collect(Collectors.toList());

        if (detailed) {
            records = events.stream().filter(e -> !e.getRecurring()).map(e -> this.entityMapped(e)).collect(Collectors.toList());

            for (Event e : events.stream().filter(Event::getRecurring).collect(Collectors.toList())) {
                VEvent ev = eventToVevent(e);

                Period period = new Period(start, end);
                Set<Period<ZonedDateTime>> recurrenceSet = ev.calculateRecurrenceSet(period);
                int index = 1;

                for (Period recurringPeriod : recurrenceSet) {
                    Event recurringEvent = new Event();
                    recurringEvent.update(e);
                    recurringEvent.setDtStart((ZonedDateTime) recurringPeriod.getStart());
                    recurringEvent.setDtEnd((ZonedDateTime) recurringPeriod.getEnd());
                    recurringEvent.setParent(e);

                    Map record = entityMapped(recurringEvent);
                    record.put("id", e.getId() + "_" + index++);

                    records.add(record);
                }
            }
        }
        
        return records;
    }

    public List<Map> eventsInRangePrint(ZonedDateTime start, ZonedDateTime end, List<Long> groups) {
        List<Map> records = eventsInRange(start, end, groups, true)
                .stream()
                .map((event) -> {
                    ZonedDateTime dtstart = ZonedDateTime.parse((String) event.get("dtstart"));
                    ZonedDateTime dtend = ZonedDateTime.parse((String) event.get("dtend"));

                    if (event.get("all_day") != null && (Boolean) event.get("all_day")) {
                        event.put("duration", Double.valueOf(0));
                    } else {
                        Duration duration = Duration.between(dtstart, dtend);
                        long totalMinutes = duration.toMinutes();
                        long hours = totalMinutes / 60;
                        long minutes = totalMinutes % 60;
                        long roundedMinutes = Math.round(minutes / 15.0) * 15;

                        if (roundedMinutes == 60) {
                            hours += 1;
                            roundedMinutes = 0;
                        }

                        Double roundedHours = hours + (roundedMinutes / 60.0);
                        event.put("duration", roundedHours);
                    }

                    event.put("dtstart", formatZDT(Locale.ITALY, dtstart));
                    event.put("dtend", formatZDT(Locale.ITALY, dtend));
                    event.put("dtstart_iso", event.get("dtstart"));

                    return event;
                })
                .collect(Collectors.toList());

        Comparator<Map> comparator = 
                Comparator.comparing((Map m) -> (String) m.get("employee_fullname") != null ? (String) m.get("employee_fullname") : "")
                .thenComparing(m -> (String) m.get("type") != null ? (String) m.get("type") : "")
                .thenComparing(m -> (String) m.get("dtstart_iso") != null ? (String) m.get("dtstart_iso") : "");

        records.sort(comparator);

        return records;
    }

    private Map<String, Object> entityMapped(Event e) {
        Map record = entityToMap(e);
        Employee employee = e.getEmployee();
        if (employee != null) {
            record.put("color", employee.getColor());
            record.put("employee_fullname", employee.getFullname());
        }

        record.put("title", e.getCustomer() != null ? e.getCustomer().getName() : null);

        return record;
    }
}
