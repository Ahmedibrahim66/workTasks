package com.example.demo.student.data.mysql;

import com.example.demo.student.models.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryMySQL extends JpaRepository<Students, Integer> {

}
