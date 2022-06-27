package com.schedulingSystem.dao.reposirory;

import com.schedulingSystem.dao.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface CourseRepository extends JpaRepository<Course, String>
{
    List<Course> findByTitle(String title);
}
