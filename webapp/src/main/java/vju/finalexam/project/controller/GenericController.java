package vju.finalexam.project.controller;

import static vju.finalexam.project.util.EntityUtils.entityToMap;
import static vju.finalexam.project.util.EntityUtils.listOfEntitiesToListOfMaps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import vju.finalexam.project.controller.dto.GetFilterDto;
import vju.finalexam.project.model.GenericEntity;
import vju.finalexam.project.repository.GenericRepository;

public abstract class GenericController<T extends GenericEntity<T>> {

    private static final Logger logger = LoggerFactory.getLogger(GenericController.class);

    private final GenericService<T> service;
    protected final GenericRepository<T> repo;

    public GenericController(GenericRepository<T> repository) {
        this.service = new GenericService<T>(repository) {};
        this.repo = repository;
    }
    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getList(@RequestBody GetFilterDto params) {
        Map<String, Object> out = new HashMap<>();
        Specification<T> specification = null;
        if (params.getFilters() != null) {
            specification = createSpecificationFromFilters(params.getFilters());
        }
        if (params.getPage() != null && params.getPage() != -1) {
            Page<T> pageRes = service.getPage(params.getPage(), params.getLimit(), params.getSortBy(), params.getSortDir(), specification);
            out.put("data", listOfEntitiesToListOfMaps(pageRes.getContent()));
            out.put("count", pageRes.getTotalElements());
        } else {
            List<T> data = service.getList(params.getSortBy(), params.getSortDir(), specification);
            out.put("data", listOfEntitiesToListOfMaps(data));
            out.put("count", data.size());
        }
        return ResponseEntity.ok(out);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(entityToMap(service.get(id)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map> update(@PathVariable Long id, @RequestBody T updated) {
        T record = service.get(id);
        if (!Objects.equals(updated.getId(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID mismatch");
        }
        return ResponseEntity.ok(entityToMap(service.update(updated)));
    }
    @PostMapping("")
    public ResponseEntity<Map> create(@RequestBody T created) {
        return ResponseEntity.ok(entityToMap(service.create(created)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map> delete(@PathVariable Long id) {
        Map record = entityToMap(service.get(id));
        service.delete(id);
        return ResponseEntity.ok(record);
    }
    private Specification<T> createSpecificationFromFilters(Map<String, Map<String, Object>> filters) {
        Specification<T> specification = Specification.where(null);
        for (Map.Entry<String, Map<String, Object>> filterEntry : filters.entrySet()) {
            String field = filterEntry.getKey();
            Map<String, Object> filterConditions = filterEntry.getValue();
            for (Map.Entry<String, Object> conditionEntry : filterConditions.entrySet()) {
                String operator = conditionEntry.getKey();
                Object value = conditionEntry.getValue();
                specification = applyCondition(specification, field, operator, value);
            }
        }
        return specification;
    }
    private Specification<T> applyCondition(Specification<T> specification, String field, String operator, Object value) {
        return switch (operator) {
            case "equals" -> specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value));
            case "greaterThan" -> specification.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(field), (Comparable) value));
            case "lessThan" -> specification.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(field), (Comparable) value));
            case "like" -> specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(field), "%" + value + "%"));
            case "ilike" -> specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), criteriaBuilder.lower(criteriaBuilder.literal("%" + value + "%"))));
            case "in" -> specification.and((root, query, criteriaBuilder) -> root.get(field).in((List<?>) value));
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
    }
}