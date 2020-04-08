package urbanTransport.dao;

import java.util.ArrayList;

public interface DAO<T> {
    void insert(T obj);
    void update(T obj);
    void delete(T obj);
}
