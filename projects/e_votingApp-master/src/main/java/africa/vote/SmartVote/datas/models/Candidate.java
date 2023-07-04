package africa.vote.SmartVote.datas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="name", nullable = false)
    private String name;
    @OneToOne
    @JoinColumn(name = "result", referencedColumnName = "id")
    private Result result;
}