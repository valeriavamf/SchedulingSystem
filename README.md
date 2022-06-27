# SchedulingSystemApplication


# Required
java 11

Rest client

# Data Base

H2 database configured to run inmemory database, which means that data will not persist on the disk

# Code

- Programing: Use Spring boot and Spring Data in order to create the rest service and manage the database conection. This framework provides a simple way to solve the problem. With spring create rest service is very simple and using String Data manage the relation from students and courses.

- Strucutre: The project implements MVC. Also the use of interfaces to keep low coupling and high cohesion.

- Test: Create unit test using Junit and Mockito.

# Services

GET all students: /schedulingSystem/student

POST student: /schedulingSystem/student
    Request body:
     {

        "id": 1,
        "firstName": "name 1",
        "lastName": "last 1"

    
}
      
PUT Student: /schedulingSystem/student
    RequestBody:
        {

        "id": 1,
        "firstName": "name update 1",
        "lastName": "last 1",
        "courses":[
            {
                "code": "course2"
            }
        ]

    
}
      
 DELETE Student: /schedulingSystem/student/{id}
 
 GET Students by field: /schedulingSystem/student 
 
    Params: id,  firstName, lastName
    
 GET Courses: /schedulingSystem/student/{id}/courses
 
    Params: id
    
 
---------------------------------------


GET all courses:/schedulingSystem/course

POST courses: /schedulingSystem/course
    Request body:
     {

        "code": "course2",
        "title": "title 1",
        "description": "desc 1"

    
}
      
PUT courses: /schedulingSystem/course{code}
    RequestBody:
        {

        "code": "course1",
        "title": "title 1",
        "description": "desc 1",
        "students":[
            {
                "id": "1"
            }
        ]

    
}
      
 DELETE courses:  /schedulingSystem/course/{code}
 
 GET courses: /schedulingSystem/course  
 
     Params: code, title
    
 GET Students: /schedulingSystem/course/{code}/students
 
    Params: code


