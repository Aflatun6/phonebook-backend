package az.phonebook.backend.repository;

import az.phonebook.backend.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findAll();

}
