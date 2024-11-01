package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;
    private final BasicUserMapper basicUserMapper;
    private final BasicUserEmailMapper basicUserEmailMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/simple")
    public List<BasicUserDto> getAllUsersBasic() {
        return userService.findAllUsers()
                          .stream()
                          .map(basicUserMapper::toDto)
                          .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                            .map(userMapper::toDto)
                            .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        try {
            User user = userMapper.toEntity(userDto);
            userService.createUser(user);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }

    @GetMapping("/email")
    public List<BasicUserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.getUsersByEmailPartial(email)
                .stream()
                .map(basicUserEmailMapper::toDto)
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(@PathVariable LocalDate time) {
        return userService.getUsersOlderThan(time)
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            User user = userService.getUser(id).orElseThrow(
                () -> new NotFoundException("User with id: " + id + " not found")
            );
            user = userMapper.updateEntity(user, userDto);
            return userService.updateUser(user);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

}