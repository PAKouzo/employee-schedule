package vju.finalexam.project.model;

public interface GenericEntity<T> {
    void update(T source);
    Long getId();
    T createNewInstance();
}
