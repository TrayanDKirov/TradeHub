package bg.sofia.uni.fmi.tdkirov.tradehub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE) // TODO Add a method changePassword
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public User(Long id) {
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.createdAt = LocalDateTime.now();
    }
}
