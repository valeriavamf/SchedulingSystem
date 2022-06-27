package com.schedulingSystem.dao.reposirory;

import com.schedulingSystem.dao.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface StudentRepository extends JpaRepository<Student, Integer >
{
    List<Student> findByFirstName(String name);

    List<Student> findByLastName(String lastname);
}
