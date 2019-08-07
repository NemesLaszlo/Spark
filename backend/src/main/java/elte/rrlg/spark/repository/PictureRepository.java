package elte.rrlg.spark.repository;

import java.util.Optional;
import elte.rrlg.spark.model.Picture;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PictureRepository extends CrudRepository<Picture, Integer>{

}
