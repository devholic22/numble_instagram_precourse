package numble.instagramprecourse.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    // @JsonIgnoreProperties(value = {"username", "password", "email", "authorities", "boards"})
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "user_id")
    private User user;
}
