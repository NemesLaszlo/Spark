package elte.rrlg.spark.repository;

import java.util.Optional;
import elte.rrlg.spark.model.Interest;
import elte.rrlg.spark.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface InterestRepository extends CrudRepository<Interest, Integer>{
    public Optional<Interest> findByFromUserAndToUser(User fromUser, User toUser);
}
