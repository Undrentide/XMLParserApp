package ua.undrentide.xmlparserapp.api;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.undrentide.xmlparserapp.service.UserService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @PostMapping("import-user")
    public String importUser(@RequestParam("multipartFile") MultipartFile multipartFile) {
        userService.importUser(multipartFile);
        return "User imported successfully.";
    }

    @GetMapping("export-user/{id}")
    public String exportUser(@PathVariable Long id,
                             HttpServletResponse response) {
        userService.exportUser(id, response);
        return "User successfully exported to file.";
    }

    @GetMapping("get-user-by-id-xlsx-list/{idList}")
    public String fetchUserListById(@PathVariable ArrayList<Long> idList,
                                    HttpServletResponse response) {
        userService.fetchUserListById(idList, response);
        return "User successfully fetched to xls list.";
    }

    @DeleteMapping("delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User successfully deleted.";
    }
}