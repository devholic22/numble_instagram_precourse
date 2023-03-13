package numble.instagramprecourse.controller;

import jakarta.validation.Valid;
import numble.instagramprecourse.entity.Board;
import numble.instagramprecourse.entity.User;
import numble.instagramprecourse.repository.BoardRepository;
import numble.instagramprecourse.repository.UserRepository;
import numble.instagramprecourse.repository.dto.UserDto;
import numble.instagramprecourse.repository.dto.UserWithBoardsDto;
import numble.instagramprecourse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public UserController(UserService userService,
                          BoardRepository boardRepository,
                          UserRepository userRepository) {
        this.userService = userService;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')") -> why not working?
    // 자신의 정보 및 자신이 올린 글들 조회
    public ResponseEntity<UserWithBoardsDto> getMyUserInfo(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return ResponseEntity.ok(userService.getUserWithBoards(user.getId()));
    }

    @GetMapping("/user/{username}")
    // @PreAuthorize("hasAnyRole('ADMIN')") -> why not working?
    // 특정 사람의 정보 및 그 사람의 글들 조회
    public ResponseEntity<UserWithBoardsDto> getUserInfo(@PathVariable String username) {
        User user = userService.getUserWithAuthorities(username).get();
        return ResponseEntity.ok(userService.getUserWithBoards(user.getId()));
    }
}
