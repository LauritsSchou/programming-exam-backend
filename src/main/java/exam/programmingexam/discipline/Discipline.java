package exam.programmingexam.discipline;

import exam.programmingexam.athlete.Athlete;
import exam.programmingexam.enums.ResultType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Discipline {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ResultType resultType;
    @ManyToMany
    private List<Athlete> athletes;
    public Discipline(int id, String name, ResultType resultType, List<Athlete> athletes) {
        this.id = id;
        this.name = name;
        this.resultType = resultType;
        this.athletes = athletes;
    }

}
