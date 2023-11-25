package project.fashionecommerce.backend.fashionecommerceproject.repository.database.follow;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends MongoRepository<FollowEntity, String> {
    Boolean existsByUserIdAndProductIdAndIsActive(String userId, String productId, Boolean isActive);
}
