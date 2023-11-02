package project.fashionecommerce.backend.fashionecommerceproject.repository.database.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Boolean existsByEmailAndIsDeleted(String email, Boolean isDeleted);
    Optional<UserEntity> findByEmailAndIsDeleted(String email, Boolean isDeleted);
    Optional<UserEntity> findByEmailAndIsDeletedAndIsActive(String email, Boolean isDeleted, Boolean isActive);
    Optional<UserEntity> findByEmailAndIsDeletedAndIsEmailActive(String email, Boolean isDeleted, Boolean isEmailActive);
}
