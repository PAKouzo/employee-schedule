package vju.finalexam.project.controller.user;

import static vju.finalexam.project.util.EntityUtils.entityToMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import vju.finalexam.project.controller.EventController;
import vju.finalexam.project.controller.dto.GetFilterDto;
import vju.finalexam.project.model.ERole;
import vju.finalexam.project.model.Role;
import vju.finalexam.project.model.User;
import vju.finalexam.project.repository.RoleRepository;
import vju.finalexam.project.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    @Autowired
    private RoleRepository roleRepo;
    
    private final UserService service;
    protected final UserRepository repo;
    public UserController(UserRepository repository) {
        this.service = new UserService(repository);
        this.repo = repository;
    }
    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getList(@RequestBody GetFilterDto params) {
        Page<User> pageRes = service.getPage(params.getPage(), params.getLimit(), params.getSortBy(), params.getSortDir());
        Map<String, Object> out = new HashMap<>();
        out.put("data", pageRes.getContent().stream()
                .map(this::secureMap)
                .collect(Collectors.toList()));
        out.put("count", pageRes.getTotalElements());

        return ResponseEntity.ok(out);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.getId()")
    public ResponseEntity<Map> getUser(@PathVariable Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                ));

        return ResponseEntity.ok(secureMap(user));
    }
    @PutMapping("/password/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.getId()")
    public ResponseEntity<Map> updatePassword(@PathVariable Long id, @RequestBody UserPasswordUpdateDto updated) {
        User record = service.get(id);
        record.setPassword(new BCryptPasswordEncoder().encode(updated.getPassword())); // Securely hash the password

        return ResponseEntity.ok(secureMap(service.update(record)));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.getId()")
    public ResponseEntity<Map> update(@PathVariable Long id, @RequestBody User updated) {
        User record = service.get(id);
        if (!Objects.equals(updated.getId(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id mismatch");
        }

        return ResponseEntity.ok(secureMap(service.update(updated)));
    }
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map> create(@RequestBody UserCreateDto created) {
        User user = new User();
        user.setUsername(created.getUsername());
        user.setEmail(created.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(created.getPassword())); // Securely hash the password

        Role role = roleRepo.findByName(ERole.valueOf(created.getRole()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "role not found"));

        user.setRoles(new HashSet<>(Arrays.asList(role)));

        return ResponseEntity.ok(secureMap(service.create(user)));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map> delete(@PathVariable Long id) {
        Map record = secureMap(service.get(id));
        service.delete(id);
        return ResponseEntity.ok(record);
    }
    private Map<String, Object> secureMap(User user) {
        Map<String, Object> res = entityToMap(user);
        res.remove("password");
        return res;
    }
}