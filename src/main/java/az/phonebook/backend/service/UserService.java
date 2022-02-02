package az.phonebook.backend.service;

import az.phonebook.backend.dto.response.UserOperation;
import az.phonebook.backend.entity.UserEntity;
import java.util.List;

public interface UserService {

    List<UserEntity> getAllUsers();
    UserOperation postUser(UserEntity userEntity);
    UserOperation editUser(UserEntity userEntity);
    UserOperation deleteUser(UserEntity userEntity);
}
