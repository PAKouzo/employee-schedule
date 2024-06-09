package vju.finalexam.project.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "employee")
public class Employee implements Serializable, GenericEntity<Employee>  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "weekhrs")
    private Double weekhrs;

    @Column(name = "active")
    private Boolean active;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    //private List<Event> events;

    // Constructors, getters, and setters
    
    @PrePersist
    @PreUpdate
    private void generateFullname() {
        this.fullname = this.name + " " + this.surname;
    }

    @Override
    public void update(Employee source) {
        this.name = source.getName();
        this.surname = source.getSurname();
        this.number = source.getNumber();
        this.phone = source.getPhone();
        this.email = source.getEmail();
        this.color = source.getColor();
        this.weekhrs = source.getWeekhrs();
        this.active = source.getActive();
    }

    @Override
    public Employee createNewInstance() {
        Employee newInstance = new Employee();
        newInstance.update(this);
        
        return newInstance;
    }
}