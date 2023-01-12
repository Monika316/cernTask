package ch.cern.todo.todo;

import ch.cern.todo.appuser.AppUser;
import ch.cern.todo.appuser.AppUserRepository;
import ch.cern.todo.category.Category;
import ch.cern.todo.category.CategoryController;
import ch.cern.todo.category.CategoryRepository;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping
@Transactional
public class TaskController {
    private AppUserRepository appUserRepository;
    private CategoryRepository categoryRepository;
    private TaskRepository taskRepository;

    public TaskController(AppUserRepository appUserRepository, CategoryRepository categoryRepository, TaskRepository taskRepository) {
        this.appUserRepository = appUserRepository;
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/task/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(()-> new NoSuchElementException());
    }

    @PostMapping("/{appUserId}/{categoryId}/task")
    public void addTask(@PathVariable Long appUserId, @PathVariable Long categoryId, @RequestBody Task task) {
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new NoSuchElementException());
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException());
        Task task1= new Task();
        task1.setTaskName(task.getTaskName());
        task1.setTaskDescription(task.getTaskDescription());
        task1.setDeadline(task.getDeadline());
        task1.setCategory(category);
        appUser.getTodoList().add(task1);
        taskRepository.save(task1);
    }

    @DeleteMapping("{appUserId}/task/{taskId}")
    public void deleteTask(@PathVariable Long appUserId, @PathVariable Long taskId) {
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new NoSuchElementException());
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new NoSuchElementException());
        appUser.getTodoList().remove(task);
        taskRepository.delete(task);
    }

}
