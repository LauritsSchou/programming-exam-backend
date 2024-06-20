package exam.programmingexam.results;

import exam.programmingexam.discipline.Discipline;
import exam.programmingexam.enums.ResultType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Results {
    @Id
    @GeneratedValue
    private int id;
    private ResultType resultType;
    private Date date;
    private String resultValue;
    @ManyToOne
    private Discipline discipline;

    public Results(int id, ResultType resultType, Date date, String resultValue, Discipline discipline) {
        this.id = id;
        this.resultType = resultType;
        this.date = date;
        this.resultValue = resultValue;
        this.discipline = discipline;
    }
}
