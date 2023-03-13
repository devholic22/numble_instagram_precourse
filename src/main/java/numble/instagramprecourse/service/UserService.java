package numble.instagramprecourse.service;

import numble.instagramprecourse.entity.Authority;
import numble.instagramprecourse.entity.Board;
import numble.instagramprecourse.entity.User;
import numble.instagramprecourse.repository.BoardRepository;
import numble.instagramprecourse.repository.UserRepository;
import numble.instagramprecourse.repository.dto.BoardDto;
import numble.instagramprecourse.repository.dto.UserDto;
import numble.instagramprecourse.repository.dto.UserWithBoardsDto;
import numble.instagramprecourse.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    @Transactional(readOnly = true)
    public UserWithBoardsDto getUserWithBoards(Long id) {
        User user = userRepository.findById(id).get();
        List<Board> boards = boardRepository.findByUserId(id);
        UserWithBoardsDto userWithBoardsDto = new UserWithBoardsDto(user, boards);
        return userWithBoardsDto;
    }
}