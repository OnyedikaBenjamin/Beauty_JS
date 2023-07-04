package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.AppUser;
import africa.vote.SmartVote.datas.models.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
public interface TokenRepository extends JpaRepository<Token, String> {
    @Modifying
    @Query("UPDATE Token token " +
            "SET token.confirmedTime = ?1 " +
            "WHERE token.id = ?2")
    void setConfirmedAt(LocalDateTime confirmedAt, String tokenId);

    Optional<Token> findByToken(String token);

    Optional<Token> findByAppUserId(String id);
    @Modifying
    @Query("UPDATE Token token " +
            "SET token.appUser.id = NULL " +
            "WHERE token.appUser.id = :deleted_Id")
    void updateTokenForDeletedUnverifiedUsers(AppUser deleted_Id);

    @Modifying
    @Query("DELETE FROM Token token " +
            "WHERE token.confirmedTime = NULL")
    void deleteUnconfirmedToken();
}
