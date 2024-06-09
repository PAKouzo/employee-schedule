package vju.finalexam.project.controller;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import vju.finalexam.project.model.GenericEntity;
import vju.finalexam.project.repository.GenericRepository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public abstract class GenericService<T extends GenericEntity<T>> {

    private final GenericRepository<T> repository;

    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> getList(String sortBy, String sortDir, Specification spec)  {
        if (sortBy != null) {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
            
            return repository.findAll(spec, sort);
        }
        
        return repository.findAll(spec);
    }

    public List<T> getListByIds(List<Long> ids)  {
        return repository.findAllById(ids);
    }
    
    public Page<T> getPage(Integer page, Integer pageSize, String sortBy, String sortDir, Specification spec){
        Sort sort = null;
        Pageable paging  = PageRequest.of(page, pageSize);
        
        if (sortBy != null) {
            sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
            paging  = PageRequest.of(page, pageSize, sort);
        }
        
        return repository.findAll(spec, paging);
    }

    public T get(Long id){
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
                  )
        );
    }

    @Transactional
    public T update(T updated){
        T dbDomain = get(updated.getId());
        dbDomain.update(updated);

        return repository.save(dbDomain);
    }

    @Transactional
    public T create(T newDomain){
        T dbDomain = newDomain.createNewInstance();
        return repository.save(dbDomain);
    }

    @Transactional
    public void delete(Long id){
        //check if object with this id exists
        get(id);
        repository.deleteById(id);
    }
}