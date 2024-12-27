package com.spring_boot_session.Repository;

import com.spring_boot_session.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}