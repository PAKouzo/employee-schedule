package vju.finalexam.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import vju.finalexam.project.controller.dto.LoginDto;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody LoginDto loginDto, HttpServletRequest request,
            HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        Map<String, Object> out = new HashMap();
        out.put("username", loginDto.getUsername());
        out.put("authenticated", authentication.isAuthenticated());
        out.put("roles", (List<String>) authentication.getAuthorities().stream().map(a -> a.getAuthority())
                .collect(Collectors.toList()));

        return ResponseEntity.ok(out);
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public ResponseEntity<String> session(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        return ResponseEntity.ok(session.getId());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return ResponseEntity.ok(true);
    }
}
