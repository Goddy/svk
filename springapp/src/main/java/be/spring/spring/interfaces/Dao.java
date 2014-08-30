package be.spring.spring.interfaces;

import java.io.Serializable;
import java.util.List;

public interface Dao<T extends Object> {
	void create(T t);
	T get(long id);
    T get(String id);
	List<T> getAll();
	void update(T t);
	void delete(T t);
	void deleteById(Serializable id);
	void deleteAll();
	long count();
}
