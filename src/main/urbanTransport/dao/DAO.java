package main.urbanTransport.dao;

public interface DAO<T> {
    void insert(T obj);
    void update(T obj);
    void delete(T obj);
}
