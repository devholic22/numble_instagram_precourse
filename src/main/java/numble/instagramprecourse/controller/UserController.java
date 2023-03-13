package numble.instagramprecourse.controller;

import jakarta.validation.Valid;
import numble.instagramprecourse.entity.User;
import numble.instagramprecourse.repository.dto.UserDto;
import numble.instagramprecourse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')") -> why not working?
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    // @PreAuthorize("hasAnyRole('ADMIN')") -> why not working?
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
