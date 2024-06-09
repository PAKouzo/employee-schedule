package vju.finalexam.project.clr;

import java.util.Arrays;
import java.util.HashSet;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.lowagie.text.List;

import ch.qos.logback.classic.Logger;
import io.micrometer.observation.Observation.Event;
import vju.finalexam.project.model.ERole;
import vju.finalexam.project.model.Role;
import vju.finalexam.project.model.User;
import vju.finalexam.project.repository.EventRepository;
import vju.finalexam.project.repository.RoleRepository;
import vju.finalexam.project.repository.UserRepository;

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