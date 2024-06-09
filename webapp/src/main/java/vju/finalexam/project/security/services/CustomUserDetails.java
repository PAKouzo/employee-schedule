package vju.finalexam.project.security.services;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private CustomUserDetails(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.authorities = builder.authorities;
        this.accountNonExpired = builder.accountNonExpired;
        this.accountNonLocked = builder.accountNonLocked;
        this.credentialsNonExpired = builder.credentialsNonExpired;
        this.enabled = builder.enabled;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private String email;
        private Set<GrantedAuthority> authorities;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withAuthorities(Set<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder withAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public Builder withAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public Builder withCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public Builder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
        public CustomUserDetails build() {
            return new CustomUserDetails(this);
        }
    }
}