package exam.programmingexam.config;

import exam.programmingexam.discipline.Discipline;
import exam.programmingexam.discipline.DisciplineRepository;
import exam.programmingexam.enums.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Discipline> disciplines = Arrays.asList(
                new Discipline(1, "100m Sprint", ResultType.TIME, null),
                new Discipline(2, "200m Sprint", ResultType.TIME, null),
                new Discipline(3, "400m Sprint", ResultType.TIME, null),
                new Discipline(4, "800m Run", ResultType.TIME, null),
                new Discipline(5, "1500m Run", ResultType.TIME, null),
                new Discipline(6, "5000m Run", ResultType.TIME, null),
                new Discipline(7, "10000m Run", ResultType.TIME, null),
                new Discipline(8, "Marathon", ResultType.TIME, null),
                new Discipline(9, "Long Jump", ResultType.DISTANCE, null),
                new Discipline(10, "High Jump", ResultType.DISTANCE, null)
        );

        disciplineRepository.saveAll(disciplines);
    }
}