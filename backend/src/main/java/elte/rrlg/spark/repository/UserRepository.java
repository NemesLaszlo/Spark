package elte.rrlg.spark.repository;

import java.util.List;
import java.util.Optional;
import elte.rrlg.spark.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

    public List<User> findAll();
    public Optional<User> findByEmail(String userName);
    public Optional<User> findById(String id);
}
