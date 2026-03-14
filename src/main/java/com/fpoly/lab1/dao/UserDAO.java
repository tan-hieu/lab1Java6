package com.fpoly.lab1.dao;

import com.fpoly.lab1.entity.J6user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<J6user, String> {
}
