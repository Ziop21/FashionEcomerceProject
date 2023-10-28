package project.fashionecommerce.backend.fashionecommerceproject.repository.database.delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends MongoRepository<DeliveryEntity, String> {
    @Query(value = "{ 'name': { $regex: ?0, $options: 'i'} }")
    Page<DeliveryEntity> customFindAll(String search, Pageable pageable);
    Boolean existsByName(String name);
}
