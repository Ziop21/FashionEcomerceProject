package project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.level;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLevelRepository extends MongoRepository<UserLevelEntity, String> {
    Boolean existsByName(String name);

    @Query(value = "{ 'name': { $regex: ?0, $options: 'i'} }")
    Page<UserLevelEntity> customFindAll(String search, PageRequest pageRequest);
}
