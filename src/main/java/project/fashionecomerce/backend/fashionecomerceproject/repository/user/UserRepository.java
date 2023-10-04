package project.fashionecomerce.backend.fashionecomerceproject.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Boolean existsByEmail(String email);

    UserEntity findByEmail(String email);
}
