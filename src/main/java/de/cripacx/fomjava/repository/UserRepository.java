package de.cripacx.fomjava.repository;

import de.cripacx.fomjava.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

    Optional<User> findByMailEquals(final String mail);

}
