package com.schedulingSystem.model;

import java.util.List;

public class CourseDto
{
    private String code;

    private String title;

    private String description;

    private List<StudentDto> students;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<StudentDto> getStudents()
    {
        return students;
    }

    public void setStudents(List<StudentDto> students)
    {
        this.students = students;
    }
}
