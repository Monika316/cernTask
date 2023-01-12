package ch.cern.todo.category;

import ch.cern.todo.appuser.AppUser;
import ch.cern.todo.appuser.AppUserRepository;
import ch.cern.todo.todo.Task;
import ch.cern.todo.todo.TaskRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Transactional
@RequestMapping("/category")
public class CategoryController {
    private CategoryRepository categoryRepository;
    private TaskRepository taskRepository;
    private AppUserRepository appUserRepository;

    public CategoryController(CategoryRepository categoryRepository, TaskRepository taskRepository, AppUserRepository appUserRepository) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()-> new NoSuchElementException());
    }

    @PostMapping("/addCategory")
    public Category addCategory(@RequestBody Category categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryDescription(categoryRequest.getCategoryDescription());
        return categoryRepository.save(category);
    }

    @DeleteMapping("/{appUserId}/{categoryId}")
    public void deleteCategory(@PathVariable Long appUserId,@PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException());
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new NoSuchElementException());
        List<Task> taskList = taskRepository.findByCategoryId(categoryId);
        taskList.forEach(task -> {
            appUser.getTodoList().remove(task);
            taskRepository.delete(task);
        });
        categoryRepository.delete(category);
    }
}
