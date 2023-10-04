package project.fashionecomerce.backend.fashionecomerceproject.dto.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.fashionecomerce.backend.fashionecomerceproject.repository.user.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(User user);

    User toDto(UserEntity userEntity);
    @Mapping(source = "isEmailActive", target = "isEmailActive")
    User updateDtoIsEmailActive(User user, Boolean isEmailActive);
}
