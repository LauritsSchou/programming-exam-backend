package exam.programmingexam.integration;

import exam.programmingexam.enums.ResultType;
import exam.programmingexam.results.Result;
import exam.programmingexam.results.ResultRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ResultServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ResultRepository resultRepository;

    @Test
    public void testGetResults() {
        ResponseEntity<Result[]> response = restTemplate.getForEntity("/results", Result[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetResult() {
        Result result = new Result();
        result.setResultType(ResultType.TIME);
        result.setDate(new Date());
        result.setResultValue("150");
        result.setDiscipline(null);
        result = resultRepository.save(result);

        ResponseEntity<Result> response = restTemplate.getForEntity("/results/" + result.getId(), Result.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getResultType()).isEqualTo(result.getResultType());
        assertThat(response.getBody().getDate()).isEqualTo(result.getDate());
        assertThat(response.getBody().getResultValue()).isEqualTo(result.getResultValue());
        assertThat(response.getBody().getDiscipline()).isEqualTo(result.getDiscipline());
    }
    @Test
    public void testCreateResult() {
        Result newResult = new Result();
        newResult.setResultType(ResultType.TIME);
        ResponseEntity<Result> response = restTemplate.postForEntity("/results", newResult, Result.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getResultType()).isEqualTo(newResult.getResultType());
    }

    @Test
    public void testUpdateResult() {
        Result result = new Result();
        result.setResultType(ResultType.TIME);
        result = resultRepository.save(result);

        result.setResultType(ResultType.DISTANCE);
        restTemplate.put("/results/" + result.getId(), result);

        Result updatedResult = resultRepository.findById(result.getId()).orElse(null);
        assertThat(updatedResult).isNotNull();
        assertThat(updatedResult.getResultType()).isEqualTo(ResultType.DISTANCE);
    }

    @Test
    public void testDeleteResult() {
        Result result = new Result();
        result = resultRepository.save(result);

        restTemplate.delete("/results/" + result.getId());

        Result deletedResult = resultRepository.findById(result.getId()).orElse(null);
        assertThat(deletedResult).isNull();
    }
}