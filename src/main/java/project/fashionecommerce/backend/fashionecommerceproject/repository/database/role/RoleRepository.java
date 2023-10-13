package project.fashionecommerce.backend.fashionecommerceproject.repository.database.role;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<RoleEntity, String> {
    Optional<RoleEntity> findByName(ERole name);
}