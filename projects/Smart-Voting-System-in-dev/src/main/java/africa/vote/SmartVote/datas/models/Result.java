package africa.vote.SmartVote.datas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="no_of_votes", nullable = false)
    private Long noOfVotes;
}