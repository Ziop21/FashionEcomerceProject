package project.fashionecommerce.backend.fashionecommerceproject.dto.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.dto.role.Role;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(User user);

    User toDto(UserEntity userEntity);
    @Mapping(source = "isEmailActive", target = "isEmailActive")
    User updateDtoIsEmailActive(User user, Boolean isEmailActive);

    @Mapping(source = "hashedPassword", target = "hashedPassword")
    User updateDtoHashedPassword(User user, String hashedPassword);

    @Mapping(source = "roles", target = "roles")
    User updateDtoRoles(User user, List<Role> roles);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    void updateExisted(User user, @MappingTarget UserEntity foundEntity);
}
