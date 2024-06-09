package vju.finalexam.project.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User implements Serializable, GenericEntity<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "password",
            nullable = false)
    private String password;
    
    @Column(name = "username",
            nullable = false,
            unique = true)
    private String username; 
    
    @Column(name = "email",
            nullable = false,
            unique = true)
    private String email; 

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(  name = "user_roles", 
          joinColumns = @JoinColumn(name = "user_id"), 
          inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;
    
    public User() {
    }

    @Override
    public void update(User source) {
      this.username = source.getUsername();
      this.email = source.getEmail();
      this.password = source.getPassword(); 
      this.roles = source.getRoles();
    }

    @Override
    public User createNewInstance() {
        User newInstance = new User();
        newInstance.update(this);
        
        return newInstance;
    }
}