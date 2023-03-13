package numble.instagramprecourse.controller;

import jakarta.validation.Valid;
import numble.instagramprecourse.entity.Board;
import numble.instagramprecourse.entity.User;
import numble.instagramprecourse.repository.BoardRepository;
import numble.instagramprecourse.repository.UserRepository;
import numble.instagramprecourse.repository.dto.BoardDto;
import numble.instagramprecourse.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public BoardController(BoardService boardService,
                           UserRepository userRepository,
                           BoardRepository boardRepository) {
        this.boardService = boardService;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @GetMapping
    public ResponseEntity<List<Board>> myBoards(Principal principal) {
        String username = principal.getName();
        List<Board> boards = boardService.findBoardsByUsername(username);
        return ResponseEntity.ok(boards);
    }

    @PostMapping
    public ResponseEntity<Board> writeBoard(@Valid @RequestBody BoardDto boardDto, Principal principal) {
        Board board = boardService.write(boardDto, principal);
        return ResponseEntity.ok(board);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Board>> findById(Long id) {
        Optional<Board> board = boardService.findById(id);
        return ResponseEntity.ok(board);
    }

}
