package com.fpoly.lab1.dao;

import com.fpoly.lab1.entity.J6userrole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleDAO extends JpaRepository<J6userrole, Long> {
}
