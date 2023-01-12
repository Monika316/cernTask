package ch.cern.todo.appuser;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class AppUserController {
    private AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/{appUserId}")
    public AppUser getUserById(@PathVariable Long appUserId) {
        return appUserRepository.findById(appUserId).orElseThrow(()-> new NoSuchElementException());
    }

    @PostMapping(path = "/addUser")
    public AppUser addUser(@RequestBody AppUser userRequest){
        AppUser user = new AppUser();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return appUserRepository.save(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        appUserRepository.delete(user);
    }

}
