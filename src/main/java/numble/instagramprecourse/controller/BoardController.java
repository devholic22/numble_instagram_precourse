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


    @PostMapping
    // 글 작성
    public ResponseEntity<Board> writeBoard(@Valid @RequestBody BoardDto boardDto, Principal principal) {
        Board board = boardService.write(boardDto, principal);
        return ResponseEntity.ok(board);
    }

    @GetMapping
    // 모든 글 조회
    public ResponseEntity<List<Board>> findAll() {
        List<Board> boards = boardService.findAll();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    // 특정 글 조회
    public ResponseEntity<Optional<Board>> findById(@PathVariable Long id) {
        Optional<Board> board = boardService.findById(id);
        return ResponseEntity.ok(board);
    }
}
