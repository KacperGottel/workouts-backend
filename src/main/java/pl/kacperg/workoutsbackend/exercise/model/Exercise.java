package pl.kacperg.workoutsbackend.exercise.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseStatus;
import pl.kacperg.workoutsbackend.user.model.User;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder(builderMethodName = "of")
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ExerciseCategory category;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "series")
    private Integer series;
    @Column(name = "reps")
    private Integer reps;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ExerciseStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id",  foreignKey = @ForeignKey(name = "fk_user_exercise"))
    private User user;
}
