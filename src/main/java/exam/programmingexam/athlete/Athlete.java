package exam.programmingexam.athlete;

import exam.programmingexam.discipline.Discipline;
import exam.programmingexam.results.Result;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Athlete {
@Id
@GeneratedValue
private int id;
private String name;
private String gender;
private int age;
private String club;
@ManyToMany
private List<Discipline> disciplines;
@OneToMany
private List<Result> results;
    public Athlete(int id, String name, String gender, int age, String club, List<Discipline> disciplines, List<Result> results) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.club = club;
        this.disciplines = disciplines;
        this.results = results;
}

    public Athlete(String name, String gender, int age, String club, List<Discipline> disciplines, List<Result> results) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.club = club;
        this.disciplines = disciplines;
        this.results = results;
    }
}
