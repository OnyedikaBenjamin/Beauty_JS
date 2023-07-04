package africa.vote.SmartVote.datas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String token;
    private LocalDateTime createdTime;
    private LocalDateTime expiredTime;
    private LocalDateTime confirmedTime;
    @OneToOne
    @JoinColumn(name="app_user", referencedColumnName="id")
    private AppUser appUser;

    public Token(String token, AppUser appUser){
        this.token = token;
        this.createdTime = LocalDateTime.now();
        this.expiredTime = LocalDateTime.now().plusMinutes(10);
        this.appUser = appUser;
    }
}
