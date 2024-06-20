package exam.programmingexam.athlete;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/athletes")
public class AthleteController {
    private final AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }
    @GetMapping
    public List<Athlete> getAthletes() {
        return athleteService.getAthletes();
    }

    @GetMapping("/{id}")
    public Athlete getAthlete(@PathVariable int id) {
        return athleteService.getAthlete(id);
    }
    @PostMapping
    public Athlete createAthlete(@RequestBody Athlete athlete) {
        return athleteService.createAthlete(athlete);
    }
    @PutMapping("/{id}")
    public Athlete updateAthlete(@PathVariable int id, @RequestBody Athlete athlete) {
        return athleteService.updateAthlete(id, athlete);
    }

    @DeleteMapping("/{id}")
    public String deleteAthlete(@PathVariable int id) {
        return athleteService.deleteAthlete(id);
    }
}

