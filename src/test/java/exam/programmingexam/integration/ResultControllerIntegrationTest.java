package exam.programmingexam.integration;

import exam.programmingexam.results.Result;
import exam.programmingexam.results.ResultService;
import exam.programmingexam.enums.ResultType;
import exam.programmingexam.discipline.Discipline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ResultControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ResultService resultService;

    private Discipline discipline1;
    private Discipline discipline2;

    private Result result1;
    private Result result2;

    @BeforeEach
    public void setup() {
        discipline1 = new Discipline(1, "Discipline One", ResultType.TIME, null);
        discipline2 = new Discipline(2, "Discipline Two", ResultType.DISTANCE, null);

        result1 = new Result(1, ResultType.TIME, LocalDate.of(2023, 6, 1), "10.5s", discipline1);
        result2 = new Result(2, ResultType.POINTS, LocalDate.of(2023, 6, 2), "20.2s", discipline2);

        when(resultService.getResult(1)).thenReturn(result1);
        when(resultService.getResult(2)).thenReturn(result2);

        List<Result> allResults = Arrays.asList(result1, result2);
        when(resultService.getResults()).thenReturn(allResults);

        when(resultService.createResult(any(Result.class))).thenAnswer(invocation -> {
            Result newResult = invocation.getArgument(0);
            newResult.setId(3);
            return newResult;
        });

        when(resultService.updateResult(eq(2), any(Result.class))).thenAnswer(invocation -> {
            Result updatedResult = invocation.getArgument(1);
            updatedResult.setId(2);
            return updatedResult;
        });

        when(resultService.deleteResult(1)).thenReturn("Result deleted successfully");
    }

    @Test
    public void testGetResultById() {
        ResponseEntity<Result> response = restTemplate.getForEntity("/results/1", Result.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result1.getId(), response.getBody().getId());
        assertEquals(result1.getResultType(), response.getBody().getResultType());
        assertEquals(result1.getDate(), response.getBody().getDate());
        assertEquals(result1.getResultValue(), response.getBody().getResultValue());
        assertEquals(result1.getDiscipline().getId(), response.getBody().getDiscipline().getId());
    }

    @Test
    public void testGetAllResults() {
        ResponseEntity<Result[]> response = restTemplate.getForEntity("/results", Result[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Result[] results = response.getBody();
        assertEquals(2, results.length);
        assertEquals(result1.getId(), results[0].getId());
        assertEquals(result2.getId(), results[1].getId());
    }

    @Test
    public void testCreateResult() {
        Result newResult = new Result(ResultType.TIME, LocalDate.of(2023, 6, 3), "30.1s", discipline1);

        // Act
        ResponseEntity<Result> response = restTemplate.postForEntity("/results", newResult, Result.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(newResult.getResultType(), response.getBody().getResultType());
        assertEquals(newResult.getDate(), response.getBody().getDate());
        assertEquals(newResult.getResultValue(), response.getBody().getResultValue());
        assertEquals(newResult.getDiscipline().getId(), response.getBody().getDiscipline().getId());
    }

    @Test
    public void testUpdateResult() {
        Result updatedResult = new Result(2, ResultType.TIME, LocalDate.of(2023, 6, 2), "22.0s", discipline2);
        ResponseEntity<Result> response = restTemplate.exchange("/results/2", HttpMethod.PUT, new HttpEntity<>(updatedResult), Result.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getId());
        assertEquals(updatedResult.getResultType(), response.getBody().getResultType());
        assertEquals(updatedResult.getDate(), response.getBody().getDate());
        assertEquals(updatedResult.getResultValue(), response.getBody().getResultValue());
        assertEquals(updatedResult.getDiscipline().getId(), response.getBody().getDiscipline().getId());
    }

    @Test
    public void testDeleteResult() {
        ResponseEntity<String> response = restTemplate.exchange("/results/1", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Result deleted successfully", response.getBody());
        verify(resultService, times(1)).deleteResult(1);
    }
}
