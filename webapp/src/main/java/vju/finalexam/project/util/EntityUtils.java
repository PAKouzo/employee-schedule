package vju.finalexam.project.util;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityUtils {    
    public static <T> List<Map> listOfEntitiesToListOfMaps(List<T> list) {
        List<Map> out = new ArrayList<>();
        for(T we:list){
            out.add(entityToMap(we));
        }
        
        return out;
    }
    
    public static <T> Map entityToMap(T entity) {
        JsonMapper mapper = JsonMapper.builder()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false)
            .addModule(new JavaTimeModule())
            .build();
        
        return mapper.convertValue(entity , Map.class);
    }   
    
}
