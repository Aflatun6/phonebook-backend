package az.phonebook.backend.service.impl;

import az.phonebook.backend.dto.response.UserOperation;
import az.phonebook.backend.entity.UserEntity;
import az.phonebook.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        UserServiceImpl.class,
        UserRepository.class
})
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity("id", "name", "phone");
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll())
                .thenReturn(List.of(user));

        List<UserEntity> allUsers = userService.getAllUsers();
        assertThat(allUsers.size(), equalTo(1));
        assertThat(allUsers.get(0), equalTo(user));
    }

    @Test
    void postUser_success() {
        when(userRepository.save(any())).thenReturn(user);

        UserOperation userOperation = userService.postUser(user);

        assertThat(userOperation.getUser_id(), equalTo("id"));
        assertThat(userOperation.getOperation_status(), equalTo("add"));
        assertThat(userOperation.getOperation_type(), equalTo("success"));
    }

    @Test
    void postUser_exception() {
        when(userRepository.save(any())).thenThrow(NullPointerException.class);

        UserOperation userOperation = userService.postUser(user);

        assertThat(userOperation.getUser_id(), equalTo("id"));
        assertThat(userOperation.getOperation_status(), equalTo("add"));
        assertThat(userOperation.getOperation_type(), equalTo("fail-class java.lang.NullPointerException"));
    }

    @Test
    void editUser_userIdIsNull() {
        UserOperation userOperation = userService.editUser(new UserEntity(null, "name", "phone"));

        assertThat(userOperation.getUser_id(), equalTo(null));
        assertThat(userOperation.getOperation_status(), equalTo("fail"));
        assertThat(userOperation.getOperation_type(), equalTo("edit"));

    }

    @Test
    void editUser_userDoesNotExist() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        UserOperation userOperation = userService.editUser(user);

        assertThat(userOperation.getUser_id(), equalTo("id"));
        assertThat(userOperation.getOperation_status(), equalTo("fail-user does not exist"));
        assertThat(userOperation.getOperation_type(), equalTo("edit"));
    }

    @Test
    void editUser_success() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        UserOperation userOperation = userService.editUser(user);

        assertThat(userOperation.getUser_id(), equalTo("id"));
        assertThat(userOperation.getOperation_status(), equalTo("success"));
        assertThat(userOperation.getOperation_type(), equalTo("edit"));
    }

    @Test
    void editUser_exception() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenThrow(NullPointerException.class);

        UserOperation userOperation = userService.editUser(user);

        assertThat(userOperation.getUser_id(), equalTo("id"));
        assertThat(userOperation.getOperation_status(), equalTo("fail-class java.lang.NullPointerException"));
        assertThat(userOperation.getOperation_type(), equalTo("edit"));
    }

    @Test
    void deleteUser_success() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        UserOperation userOperation = userService.deleteUser(user);

        assertThat(userOperation.getUser_id(), equalTo("id"));
        assertThat(userOperation.getOperation_status(), equalTo("success"));
        assertThat(userOperation.getOperation_type(), equalTo("delete"));
    }

    @Test
    void deleteUser_userIdIsNull() {
        UserOperation userOperation = userService.deleteUser(new UserEntity(null, "name", "phone"));

        assertThat(userOperation.getUser_id(), equalTo(null));
        assertThat(userOperation.getOperation_status(), equalTo("fail-user id is null"));
        assertThat(userOperation.getOperation_type(), equalTo("delete"));
    }

    @Test
    void deleteUser_exception() {
        when(userRepository.findById(any())).thenThrow(NullPointerException.class);

        UserOperation userOperation = userService.deleteUser(user);

        assertThat(userOperation.getUser_id(), equalTo("id"));
        assertThat(userOperation.getOperation_status(), equalTo("fail-class java.lang.NullPointerException"));
        assertThat(userOperation.getOperation_type(), equalTo("delete"));
    }
}