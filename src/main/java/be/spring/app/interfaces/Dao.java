package be.spring.app.interfaces;

import java.util.List;

public interface Dao<T extends Object> {
	void create(T t);
	T get(long id);
    T get(String id);
	List<T> getAll();
	void update(T t);
	void delete(T t);
    void delete(String id);
	void deleteAll();
	long count();
}
