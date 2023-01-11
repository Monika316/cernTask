package ch.cern.todo.appuser;

import ch.cern.todo.todo.Task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> todoList = new ArrayList<>();

    public AppUser(Long id, String username, String password, List<Task> todoList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.todoList = todoList;
    }

    public  AppUser () {

    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Task> getTodoList() {
        return todoList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTodoList(List<Task> todoList) {
        this.todoList = todoList;
    }

}
