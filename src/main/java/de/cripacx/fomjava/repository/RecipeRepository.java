package de.cripacx.fomjava.repository;

import de.cripacx.fomjava.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, UUID> {

    List<Recipe> findAllByCreatorEquals(UUID creator);

}
