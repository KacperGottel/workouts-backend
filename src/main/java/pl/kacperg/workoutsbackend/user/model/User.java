package pl.kacperg.workoutsbackend.user.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.kacperg.workoutsbackend.exercise.model.Exercise;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Exercise> exercises;

    @Builder(builderMethodName = "of")
    public User(Long id, String email, String username, String password, Scope scope, LocalDateTime created, LocalDateTime expired, UserStatus userStatus) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.scope = scope;
        this.created = created;
        this.expired = expired;
        this.userStatus = userStatus;
    }

}
