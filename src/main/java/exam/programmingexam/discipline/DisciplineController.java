package exam.programmingexam.discipline;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {
    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }
    @GetMapping
    public List<Discipline> getDisciplines() {
        return disciplineService.getDisciplines();
    }

    @GetMapping("/{id}")
    public Discipline getDiscipline(@PathVariable int id) {
        return disciplineService.getDiscipline(id);
    }
    @PostMapping
    public Discipline createDiscipline(@RequestBody Discipline discipline) {
        return disciplineService.createDiscipline(discipline);
    }
    @PutMapping("/{id}")
    public Discipline updateDiscipline(@PathVariable int id, @RequestBody Discipline discipline) {
        return disciplineService.updateDiscipline(id, discipline);
    }

    @DeleteMapping("/{id}")
    public String deleteDiscipline(@PathVariable int id) {
        return disciplineService.deleteDiscipline(id);
    }
}
