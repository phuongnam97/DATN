package project3.ginp14.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project3.ginp14.entity.Role;
import project3.ginp14.entity.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByUsernameAndEmailAndTelephone(String username, String email, String telephone);
    List<User> findAll();
    User findById(int id);
    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT u.* FROM users u where u.role_id <> ?1", nativeQuery = true)
    List<User> findAllByRoleIsNotAdmin(int roleId);
}
