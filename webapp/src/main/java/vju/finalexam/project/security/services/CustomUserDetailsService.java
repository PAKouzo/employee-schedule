package vju.finalexam.project.security.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vju.finalexam.project.model.User;
import vju.finalexam.project.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist by username or email"));
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
        return new CustomUserDetails.Builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withAuthorities(authorities)
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                .build();
    }
}