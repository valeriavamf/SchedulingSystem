package com.schedulingSystem.dao.reposirory;

import com.schedulingSystem.dao.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer >
{
    List<Student> findByFirstName(String name);

    List<Student> findByLastName(String lastname);
}
