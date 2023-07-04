package africa.vote.SmartVote.datas.models;

import africa.vote.SmartVote.datas.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="title", nullable = false, length = 500)
    private String title;
    @Column(name="question", nullable = false, length = 1000)
    private String question;
    @Column(name="start_date_time", nullable = false)
    private LocalDateTime startDateTime;
    @Column(name="end_date_time", nullable = false)
    private LocalDateTime endDateTime;
    @JsonIgnore
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    @ManyToOne()
    private AppUser users;
    @Enumerated(EnumType.STRING)
    private Category category;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate", referencedColumnName = "id")
    private List<Candidate>candidates;
}