package edu.icet.solorent.controller;

import edu.icet.solorent.dto.User;
import edu.icet.solorent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solo-rent")
@CrossOrigin
public class UserController {
    final UserService userService;

    @PostMapping("")
    public void add(@RequestBody User user) {
    }

    @DeleteMapping("")
    public void delete(@PathVariable Long id) {
    }

    @PutMapping("")
    public void update(@RequestBody User user) {
    }

    @GetMapping("")
    public List<User> getAll() {
        return List.of();
    }

}
