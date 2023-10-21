package project.fashionecommerce.backend.fashionecommerceproject.repository.database.size;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends MongoRepository<SizeEntity, String>{
    @Query(value = "{ 'name': { $regex: ?0, $options: 'i'} }")
    Page<SizeEntity> customerFindAll(String search, Pageable pageable);
    Boolean existsByName(String name);
}
