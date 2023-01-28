package pl.kacperg.workoutsbackend.user.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder(builderMethodName = "of")
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "scope")
    private Scope scope;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "expired")
    private LocalDateTime expired;
    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

}
