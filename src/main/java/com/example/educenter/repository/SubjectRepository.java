package com.example.educenter.repository;

import com.example.educenter.model.Subject;
import com.example.educenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    List<Subject> findAll();

    Subject findOneById(int id);

    List<Subject> findAllByUsersIsContaining(User user);
}
