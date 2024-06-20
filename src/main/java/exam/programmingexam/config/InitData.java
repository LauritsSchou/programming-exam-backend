package exam.programmingexam.config;

import exam.programmingexam.athlete.Athlete;
import exam.programmingexam.athlete.AthleteRepository;
import exam.programmingexam.discipline.Discipline;
import exam.programmingexam.discipline.DisciplineRepository;
import exam.programmingexam.enums.ResultType;
import exam.programmingexam.results.Result;
import exam.programmingexam.results.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Athlete> athletes;
        List<Result> results;
        List<Discipline> disciplines;

        disciplines = Arrays.asList(
                new Discipline(1, "100m Sprint", ResultType.TIME, null),
                new Discipline(2, "200m Sprint", ResultType.TIME, null),
                new Discipline(3, "400m Sprint", ResultType.TIME, null),
                new Discipline(4, "800m Run", ResultType.TIME, null),
                new Discipline(5, "1500m Run", ResultType.TIME, null),
                new Discipline(6, "5000m Run", ResultType.TIME, null),
                new Discipline(7, "10000m Run", ResultType.TIME, null),
                new Discipline(8, "Marathon", ResultType.TIME, null),
                new Discipline(9, "Long Jump", ResultType.DISTANCE, null),
                new Discipline(10, "High Jump", ResultType.DISTANCE, null),
                new Discipline(11, "Shot Put", ResultType.POINTS, null),
                new Discipline(12, "Javelin Throw", ResultType.DISTANCE, null)
        );

        disciplineRepository.saveAll(disciplines);

        results = Arrays.asList(
                new Result(1, ResultType.TIME, LocalDate.now(), "00:00:22:23", disciplines.get(0)),
                new Result(2, ResultType.TIME, LocalDate.now(), "00:00:43:24", disciplines.get(1)),
                new Result(3, ResultType.TIME, LocalDate.now(), "00:00:1:23", disciplines.get(2)),
                new Result(4, ResultType.TIME, LocalDate.now(), "00:02:24:45", disciplines.get(3)),
                new Result(5, ResultType.TIME, LocalDate.now(), "00:04:42:56", disciplines.get(4)),
                new Result(6, ResultType.TIME, LocalDate.now(), "00:15:15:23", disciplines.get(5)),
                new Result(7, ResultType.TIME, LocalDate.now(), "00:30:30:45", disciplines.get(6)),
                new Result(8, ResultType.TIME, LocalDate.now(), "02:30:45:23", disciplines.get(7)),
                new Result(9, ResultType.DISTANCE, LocalDate.now(), "5.6", disciplines.get(8)),
                new Result(10, ResultType.DISTANCE, LocalDate.now(), "1.7", disciplines.get(9)),
                new Result(11, ResultType.POINTS, LocalDate.now(), "250", disciplines.get(10)),
                new Result(12, ResultType.DISTANCE, LocalDate.now(), "30.5", disciplines.get(11))
        );

        resultRepository.saveAll(results);

        athletes = Arrays.asList(
                new Athlete(1, "John Doe", "Male", 25, "Club 1", disciplines.subList(0, 5), results.subList(0, 5)),
                new Athlete(2, "Jane Doe", "Female", 23, "Club 2", disciplines.subList(5, 10), results.subList(5, 10)),
                new Athlete(3, "Alice", "Female", 27, "Club 3", disciplines.subList(10, 12), results.subList(10, 12)),
                new Athlete(4, "Bob", "Male", 30, "Club 4", disciplines.subList(10, 11), null)
        );

        athleteRepository.saveAll(athletes);
    }
}
