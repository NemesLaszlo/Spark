package elte.rrlg.spark.repository;

import org.springframework.stereotype.Repository;

import elte.rrlg.spark.model.MatchUnlock;

import org.springframework.data.repository.CrudRepository;

/**
 * MatchUnlocksRepository
 */
@Repository
public interface MatchUnlockRepository extends CrudRepository<MatchUnlock, Integer> {

    
}