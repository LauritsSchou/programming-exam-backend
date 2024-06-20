package exam.programmingexam.results;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }
    @GetMapping
    public List<Result> getResults() {
        return resultService.getResults();
    }
    @GetMapping("/{id}")
    public Result getResult(@PathVariable int id) {
        return resultService.getResult(id);
    }
    @PostMapping
    public Result createResult(@RequestBody Result result) {
        return resultService.createResult(result);
    }
    @PutMapping("/{id}")
    public Result updateResult(@PathVariable int id, @RequestBody Result result) {
        return resultService.updateResult(id, result);
    }
    @DeleteMapping("/{id}")
    public String deleteResult(@PathVariable int id) {
        return resultService.deleteResult(id);
    }

}

