package elte.rrlg.spark.repository;

import java.util.Optional;
import elte.rrlg.spark.model.Rating;
import elte.rrlg.spark.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer>{
    public Optional<Rating> findByFromUserAndToUser(User fromUser, User toUser);
}
