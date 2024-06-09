package vju.finalexam.project.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "customer")
public class Customer implements Serializable, GenericEntity<Customer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "vat", nullable = false)
    private String vat;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @Override
    public void update(Customer source) {
        this.vat = source.getVat();
        this.name = source.getName();
        this.address = source.getAddress();
        this.zipcode = source.getZipcode();
        this.city = source.getCity();
        this.phone = source.getPhone();
        this.email = source.getEmail();
        this.website = source.getWebsite();
    }

    @Override
    public Customer createNewInstance() {
        Customer newInstance = new Customer();
        newInstance.update(this);
        
        return newInstance;
    }
}