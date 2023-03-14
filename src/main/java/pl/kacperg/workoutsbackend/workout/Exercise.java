package pl.kacperg.workoutsbackend.workout;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

}
