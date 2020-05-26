package DAO;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface DAO<A> {
    List<A> getAll();
    List<A> getBy(Predicate<A> p);
    Optional<A> get(String id);
    void put(A a);
    void delete(String id);
}
