package project.fashionecomerce.backend.fashionecomerceproject.repository.size;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends MongoRepository<SizeEntity, String>{
    @Query(value = "{ 'name': { $regex: ?0} }")
    Page<SizeEntity> customerFindAll(String search, Pageable pageable);
}
