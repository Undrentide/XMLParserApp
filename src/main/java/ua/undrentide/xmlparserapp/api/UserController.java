package ua.undrentide.xmlparserapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.undrentide.xmlparserapp.entity.User;
import ua.undrentide.xmlparserapp.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @PostMapping("add_user")
    public String addUser() {
        userService.addUser();
        return "User successfully added.";
    }

    @GetMapping("export_user/{id}")
    public String exportUser(@PathVariable Long id) {
        userService.exportUser(id);
        return "User successfully exported to file.";
    }

    @GetMapping("get_users")
    public List<User> fetchUserList() {
        return userService.fetchUserList();
    }

    @DeleteMapping("delete_user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User successfully deleted.";
    }
}