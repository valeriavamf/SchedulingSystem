package com.schedulingSystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.persistence.Id;



@Entity
@Table(name = "task")
class Task {

    @Id
    private Long id;
    @Column
    private String description;
    private BigInteger priority;

    public Long getId(){
        return id;

    }
    public void setId(Long id){
        this.id = id;
    }

    public String getDescription(){
        return description;

    }
    public void setDescription(String description){
        this.description = description;

    }

    public BigInteger getPriority(){
        return priority;

    }
    public void setPriority(BigInteger priority){
        this.priority = priority;

    }
}


class TaskDTO {

    private Long id;
    private String description;
    private BigInteger priority;

    public Long getId(){
        return id;

    }
    public void setId(Long id){
        this.id = id;
    }

    public String getDescription(){
        return description;

    }
    public void setDescription(String description){
        this.description = description;

    }

    public BigInteger getPriority(){
        return priority;

    }
    public void setPriority(BigInteger priority){
        this.priority = priority;

    }
}

class ResponseDTO{
    private String message;
    private int status;

    public ResponseDTO(){}
    public ResponseDTO(String message, int status){
        this.message = message;
        this.status = status;
    }
    public String getMessage(){
        return message;

    }
    public void getMessage(String message){
        this.message = message;

    }

    public int getStatus(){
        return status;

    }
    public void setStatus(int status){
        this.status = status;

    }

}

@Service
class TaskService {
    @Autowired
    private TaskRepository repository;

    public ResponseEntity updateTask(Long id, TaskDTO task){



        if(id == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (task == null
                || task.getDescription() == null
                || task.getDescription().isEmpty() ){

            return  new ResponseEntity(new ResponseDTO("Task description is required",HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
        }


        final Optional<Task> entity = repository.findById(id);
        if (!entity.isPresent()){
            Task newTask = new Task();
            newTask.setId(id);
            newTask.setDescription(task.getDescription());
            newTask.setPriority(task.getPriority());

            repository.save(newTask);

            return ResponseEntity.ok("ok new");

        }

        try
        {
            /*repository.delete(entity.get());

            Task newTask = new Task();
            newTask.setId(id);
            newTask.setDescription(task.getDescription());
            newTask.setPriority(task.getPriority());*/

            final Task task1 = entity.get();
            task1.setDescription(task.getDescription());
            task1.setPriority(task.getPriority());

            final Task save = repository.save(task1);
            return ResponseEntity.ok(save);
        }
        catch (JpaSystemException | IllegalArgumentException ex)
        {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

@RestController
@RequestMapping("/tasks")
class TaskController {
    private static Logger log = Logger.getLogger("Solution");
    @Autowired
    private TaskService service;
    // log.info("You can use 'log' for debug messages");

    @PutMapping("/{id}")
    public ResponseEntity putTask(@PathVariable Long id, @RequestBody TaskDTO task)
    {
        return service.updateTask(id, task);
    }
}

@Repository
interface TaskRepository extends JpaRepository<Task, Long> {
}
