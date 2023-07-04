package africa.vote.SmartVote.datas.models;

import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Role;
import africa.vote.SmartVote.datas.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="first_name", nullable = false, length = 100)
    private String firstName;
    @Column(name="last_name", length = 100)
    private String lastName;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "status", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "image_url", nullable = false)
    private String imageURL;
    @Enumerated(EnumType.STRING)
    private Category category;
    @JsonIgnore
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}