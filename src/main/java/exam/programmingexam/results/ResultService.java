package exam.programmingexam.results;

import exam.programmingexam.athlete.Athlete;
import exam.programmingexam.athlete.AthleteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ResultService {
    private final ResultRepository resultRepository;
    private final AthleteRepository athleteRepository;
    public ResultService(ResultRepository resultRepository, AthleteRepository athleteRepository) {
        this.resultRepository = resultRepository;
        this.athleteRepository = athleteRepository;
    }
    public List<Result> getResults() {
        return resultRepository.findAll();
    }
    public Result getResult(int id) {
        return resultRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result with id" + id + "not found"));
    }
    public Result createResult(Result result) {
        return resultRepository.save(result);
    }
    public Result updateResult(int id, Result result) {
        if (resultRepository.existsById(id)) {
            result.setId(id);
            return resultRepository.save(result);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result with id" + id + "not found");
        }
    }
    public String deleteResult(int id) {
        if (resultRepository.existsById(id)) {
            Result result = getResult(id);
            Athlete athlete = athleteRepository.findByResultId(id);
            if (athlete != null) {
                athlete.getResults().remove(result);
                athleteRepository.save(athlete);
            }
            resultRepository.deleteById(id);
            return "Result deleted";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result with id" + id + "not found");
        }
    }
    }


