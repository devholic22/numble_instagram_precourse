package numble.instagramprecourse.repository;

import numble.instagramprecourse.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByUserId(Long userId);
    Board save(Board board);
}
