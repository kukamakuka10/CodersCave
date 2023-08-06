package ua.kurapatkadev.coderscave.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.kurapatkadev.coderscave.dto.users.RegistrationUserDTO;
import ua.kurapatkadev.coderscave.dto.users.UserDTO;
import ua.kurapatkadev.coderscave.entities.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);

    User mapToEntity(RegistrationUserDTO registrationUserDTO);
    UserDTO mapToDto(User user);
}
