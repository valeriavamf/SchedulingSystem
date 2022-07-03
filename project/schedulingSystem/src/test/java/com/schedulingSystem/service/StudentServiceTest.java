package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.dao.reposirory.StudentRepository;
import com.schedulingSystem.model.StudentDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentServiceTest
{

    @Mock
    StudentRepository studentRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    StudentService service;

    private StudentDto student = new StudentDto();
    private Student entity = new Student();

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        student.setId(1);
        student.setFirstName("name");
        student.setLastName("last");

        entity.setId(1);
        entity.setFirstName("name");
        entity.setLastName("last");
    }

    @Test
    public void getAllStudents_CheckStudentsAreRetrived()
    {

        List<Student> students = new ArrayList<>();
        students.add(entity);

        when(studentRepository.findAll()).thenReturn(students);
        when(modelMapper.map(isA(Student.class), any())).thenReturn(student);

        final List<StudentDto> allStudents = service.getAllStudents(null);

        assertNotNull(allStudents);
        assertEquals(allStudents.size(), 1);

    }

    @Test
    public void saveStudent_CheckStudentIsSave()
    {

        when(modelMapper.map(isA(StudentDto.class), any())).thenReturn(entity);
        when(studentRepository.save(isA(Student.class))).thenReturn(entity);


        final String saveStudent = service.saveStudent(student);

        assertEquals(saveStudent,"OK");

    }

    @Test
    public void updateCourse_CheckStudentIsSave()
    {

        when(studentRepository.findById(isA(Integer.class))).thenReturn(Optional.of(entity));
        when(modelMapper.map(isA(StudentDto.class), any())).thenReturn(entity);
        when(studentRepository.save(isA(Student.class))).thenReturn(entity);


        final String updateStudent = service.updateStudent(student);

        verify(studentRepository, times(1)).delete(isA(Student.class));

        assertEquals(updateStudent,"OK");

    }

    @Test
    public void deleteStudent_CheckStudentIsDeleted()
    {

        when(studentRepository.findById(isA(Integer.class))).thenReturn(Optional.of(entity));

        final String deletedStudent = service.deleteStudent(1);

        verify(studentRepository, times(1)).delete(isA(Student.class));

        assertEquals(deletedStudent,"OK");

    }
}