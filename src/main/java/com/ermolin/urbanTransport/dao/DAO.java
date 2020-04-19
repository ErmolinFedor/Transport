package main.java.com.ermolin.urbanTransport.dao;

public interface DAO<T> {
    void insert(T obj);
    void update(T obj);
    void delete(T obj);
}
