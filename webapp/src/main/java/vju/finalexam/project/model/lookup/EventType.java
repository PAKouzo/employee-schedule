package vju.finalexam.project.model.lookup;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@Table(name = "lkp_event_type")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EventType implements Serializable {
    @Id
    @Column(name = "code")
    private String code;
}