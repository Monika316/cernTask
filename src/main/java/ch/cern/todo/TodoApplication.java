package ch.cern.todo;

import ch.cern.todo.appuser.AppUser;
import ch.cern.todo.appuser.AppUserRepository;
import ch.cern.todo.category.Category;
import ch.cern.todo.category.CategoryRepository;
import ch.cern.todo.todo.Task;
import ch.cern.todo.todo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class TodoApplication implements CommandLineRunner {

	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private TaskRepository taskRepository;

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AppUser appUser = new AppUser();
		appUser.setId(1L);
		appUser.setPassword("password");
		appUser.setUsername("Monika");

		Category category = new Category();
		category.setId(1L);
		category.setCategoryName("WORK");
		category.setCategoryDescription("tasks from my boss");

		Task task = new Task();
		task.setId(1L);
		task.setTaskName("1 task");
		task.setTaskDescription("desc");
		task.setDeadline(LocalDateTime.now());
		task.setCategory(category);

		appUser.getTodoList().add(task);

		appUserRepository.save(appUser);
		categoryRepository.save(category);
		//TimeUnit.SECONDS.sleep(5);
		taskRepository.save(task);

	}
}
