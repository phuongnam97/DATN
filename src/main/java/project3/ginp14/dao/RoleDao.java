package project3.ginp14.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project3.ginp14.entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
}
