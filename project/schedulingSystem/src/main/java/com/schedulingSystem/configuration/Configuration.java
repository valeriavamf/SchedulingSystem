package com.schedulingSystem.configuration;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration
{
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Course.class,CourseDto.class).addMappings(mapper -> {
            mapper.skip(CourseDto::setStudents);
        });

        modelMapper.typeMap(Student.class,StudentDto.class).addMappings(mapper -> {
            mapper.skip(StudentDto::setCourses);
        });

        return modelMapper;
    }
}
