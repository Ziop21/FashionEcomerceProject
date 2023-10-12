package project.fashionecomerce.backend.fashionecomerceproject.repository.color;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface ColorRepository extends MongoRepository<ColorEntity, String> {
    @Query(value = "{ 'name': { $regex: ?0} }")
    Page<ColorEntity> customerFindAll(String search, Pageable pageable);
    Boolean existsByName(String name);
}
