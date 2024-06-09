package vju.finalexam.project.clr;

import static vju.finalexam.project.repository.specification.EventSpecifications.recurring;
import static vju.finalexam.project.util.EventUtils.eventToRRuleString;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vju.finalexam.project.model.Event;
import vju.finalexam.project.repository.EventRepository;

@Component
public class RRuleUpdater implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(RRuleUpdater.class);

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Event> recurringEvents = eventRepository.findAll(recurring());
        for (Event e : recurringEvents) {
            if (StringUtils.isEmpty(e.getRrule())) {
                e.setRrule(eventToRRuleString(e));

                eventRepository.save(e);
            }
        }
    }
}