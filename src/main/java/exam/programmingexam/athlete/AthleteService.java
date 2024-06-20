package exam.programmingexam.athlete;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AthleteService {
    private final AthleteRepository athleteRepository;

    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }


    public List<Athlete> getAthletes() {
        return athleteRepository.findAll();
    }

    public Athlete getAthlete(int id) {
        return athleteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Athlete with id" + id + "not found"));
    }
    public Athlete createAthlete(Athlete athlete) {
        return athleteRepository.save(athlete);
    }

    public Athlete updateAthlete(int id, Athlete athlete) {
        if (athleteRepository.existsById(id)) {
            athlete.setId(id);
            return athleteRepository.save(athlete);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Athlete with id" + id + "not found");
        }
    }

    public String deleteAthlete(int id) {
        if (athleteRepository.existsById(id)) {
            athleteRepository.deleteById(id);
            return "Athlete deleted";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Athlete with id" + id + "not found");
        }
    }
}

