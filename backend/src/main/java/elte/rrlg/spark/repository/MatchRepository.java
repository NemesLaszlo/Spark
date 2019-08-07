package elte.rrlg.spark.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import elte.rrlg.spark.model.Match;
import elte.rrlg.spark.model.User;

@Repository
public interface MatchRepository extends CrudRepository<Match, Integer>{
    public List<Match> findByUserAOrUserB(User userA, User userB);
}
