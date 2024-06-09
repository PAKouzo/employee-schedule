package vju.finalexam.project.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import vju.finalexam.project.model.User;

@Repository
public interface UserRepository extends  GenericRepository<User> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);
}