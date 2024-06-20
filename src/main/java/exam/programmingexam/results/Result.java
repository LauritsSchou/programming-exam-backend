package exam.programmingexam.results;

import exam.programmingexam.athlete.Athlete;
import exam.programmingexam.discipline.Discipline;
import exam.programmingexam.enums.ResultType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue
    private int id;
    private ResultType resultType;
    private LocalDate date;
    private String resultValue;
    @ManyToOne
    private Discipline discipline;

    public Result(int id, ResultType resultType, LocalDate date, String resultValue, Discipline discipline) {
        this.id = id;
        this.resultType = resultType;
        this.date = date;
        this.resultValue = resultValue;
        this.discipline = discipline;
    }

    public Result(ResultType resultType, LocalDate date, String resultValue, Discipline discipline) {
        this.resultType = resultType;
        this.date = date;
        this.resultValue = resultValue;
        this.discipline = discipline;
    }
}
