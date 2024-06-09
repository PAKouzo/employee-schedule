package vju.finalexam.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import vju.finalexam.project.model.GenericEntity;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity<T>> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
