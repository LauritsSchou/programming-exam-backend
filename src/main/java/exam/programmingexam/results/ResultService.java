package exam.programmingexam.results;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ResultService {
    private final ResultRepository resultRepository;
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
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
            resultRepository.deleteById(id);
            return "Result deleted";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result with id" + id + "not found");
        }
    }

}
