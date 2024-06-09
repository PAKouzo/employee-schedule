package vju.finalexam.project.controller.user;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import vju.finalexam.project.model.User;
import vju.finalexam.project.repository.UserRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Page<User> getPage(Integer page, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        Pageable paging = PageRequest.of(page, pageSize);

        if (sortBy != null) {
            sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            paging = PageRequest.of(page, pageSize, sort);
        }

        return repository.findAll(paging);
    }

    public User get(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"));
    }

    @Transactional
    public User update(User updated) {
        User dbDomain = get(updated.getId());
        dbDomain.update(updated);

        return repository.save(dbDomain);
    }

    @Transactional
    public User create(User newDomain) {
        User dbDomain = newDomain.createNewInstance();
        return repository.save(dbDomain);
    }

    @Transactional
    public void delete(Long id) {
        // check if object with this id exists
        get(id);
        repository.deleteById(id);
    }
}