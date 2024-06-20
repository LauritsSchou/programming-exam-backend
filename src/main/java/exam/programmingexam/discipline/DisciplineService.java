package exam.programmingexam.discipline;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;
    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }
    public List<Discipline> getDisciplines() {
        return disciplineRepository.findAll();
    }
    public Discipline getDiscipline(int id) {
        return disciplineRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline with id" + id + "not found"));
    }
    public Discipline createDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }
    public Discipline updateDiscipline(int id, Discipline discipline) {
        if (disciplineRepository.existsById(id)) {
            discipline.setId(id);
            return disciplineRepository.save(discipline);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline with id" + id + "not found");
        }
    }
    public String deleteDiscipline(int id) {
        if (disciplineRepository.existsById(id)) {
            disciplineRepository.deleteById(id);
            return "Discipline deleted";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline with id" + id + "not found");
        }
    }
}
