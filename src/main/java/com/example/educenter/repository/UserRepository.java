package com.example.educenter.repository;

import com.example.educenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneByEmail(String email);

    User findOneById(int id);


}
