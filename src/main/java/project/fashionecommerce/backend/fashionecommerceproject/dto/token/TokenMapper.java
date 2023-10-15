package project.fashionecommerce.backend.fashionecommerceproject.dto.token;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.token.TokenEntity;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    Token toDto(TokenEntity refreshTokenEntity);

    TokenEntity toEntity(Token token);
}
