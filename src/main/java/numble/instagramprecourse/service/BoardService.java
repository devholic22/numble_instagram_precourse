package numble.instagramprecourse.service;

import numble.instagramprecourse.entity.Board;
import numble.instagramprecourse.entity.User;
import numble.instagramprecourse.repository.BoardRepository;
import numble.instagramprecourse.repository.UserRepository;
import numble.instagramprecourse.repository.dto.BoardDto;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository,
                        UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public List<Board> findBoardsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return boardRepository.findByUserId(user.getId());
    }

    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    public Board write(BoardDto boardDto, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .user(user)
                .build();
        userRepository.save(user);
        return boardRepository.save(board);
    }
}
