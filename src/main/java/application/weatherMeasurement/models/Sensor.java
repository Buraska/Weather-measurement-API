package application.weatherMeasurement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Sensor {

    @Id
    @SequenceGenerator(name = "seq1", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(generator = "seq1", strategy = GenerationType.SEQUENCE)
    @NonNull
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 256)
    @Column(unique = true)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "sensor")
    private Set<Measurement> measurements;

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
