package numble.instagramprecourse.repository.dto;

import lombok.Getter;
import lombok.Setter;
import numble.instagramprecourse.entity.Board;
import numble.instagramprecourse.entity.User;

import java.util.List;

@Getter
@Setter
public class UserWithBoardsDto {
    private User user;
    private List<Board> boards;

    public UserWithBoardsDto(User user, List<Board> boards) {
        this.user = user;
        this.boards = boards;
    }
}
