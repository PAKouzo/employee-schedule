package vju.finalexam.project.controller.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.Data;

@Data
public class EventBetweenDatesDto {
    private ZonedDateTime start;
    private ZonedDateTime end;
    private List<Long> groups;        
    private boolean detailed = false;
}