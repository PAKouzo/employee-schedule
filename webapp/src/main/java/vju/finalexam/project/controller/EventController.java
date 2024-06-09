package vju.finalexam.project.controller;

import jakarta.servlet.http.HttpServletResponse;

import static vju.finalexam.project.util.DateUtils.formatZDT;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vju.finalexam.project.controller.dto.EventBetweenDatesDto;
import vju.finalexam.project.model.Event;
import vju.finalexam.project.repository.EventRepository;

@RestController
@RequestMapping("/api/event")
public class EventController extends GenericController<Event> {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    
    @Autowired
    EventRepository repo;

    public EventController(EventRepository repo) {
        super(repo);
    }

    @PostMapping("/in")
    public ResponseEntity<Map<String, Object>> getBetweenDates(@RequestBody EventBetweenDatesDto body) {
        List<Map> records = repo.eventsInRange(body.getStart(), body.getEnd(), body.getGroups(), body.isDetailed());
        
        return ResponseEntity.ok(Map.of(
                "data", records,
                "count", records.size()));
    }
    
    @RequestMapping(value = "/print", method = RequestMethod.GET)
    @ResponseBody
    public void getEventListReport(HttpServletResponse response, 
            @RequestParam(required = false, defaultValue = "en") 
                  String locale,
            @RequestParam(required = false) 
                  ZonedDateTime start,
            @RequestParam(required = false) 
                  ZonedDateTime end,
            @RequestParam(required = false) 
                  String employees) throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/EventList-"+locale+".jasper");
        
        Map<String,Object> params = new HashMap<>();
        params.put("start", formatZDT(Locale.forLanguageTag(locale), start));
        params.put("end", formatZDT(Locale.forLanguageTag(locale), end));
        
        List<Long> groups = null;
        if (employees != null)
               groups = Arrays.asList(employees.split(","))
                    .stream().map(Long::parseLong)
                    .collect(Collectors.toList());
        
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(repo.eventsInRangePrint(start,end,groups));
      
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, datasource);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=event-list.pdf");

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}
