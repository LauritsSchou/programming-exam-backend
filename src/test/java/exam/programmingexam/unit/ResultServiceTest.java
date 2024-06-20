package exam.programmingexam.unit;

import exam.programmingexam.athlete.Athlete;
import exam.programmingexam.athlete.AthleteRepository;
import exam.programmingexam.results.Result;
import exam.programmingexam.results.ResultRepository;
import exam.programmingexam.results.ResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResultServiceTest {
    private ResultRepository resultRepository;
    private AthleteRepository athleteRepository;
    private ResultService resultService;

    @BeforeEach
    public void setup() {
        resultRepository = mock(ResultRepository.class);
        athleteRepository = mock(AthleteRepository.class);
        resultService = new ResultService(resultRepository, athleteRepository);
    }

    @Test
    public void testGetResults() {
        Result result1 = new Result();
        Result result2 = new Result();
        when(resultRepository.findAll()).thenReturn(Arrays.asList(result1, result2));

        List<Result> results = resultService.getResults();

        assertEquals(2, results.size());
        verify(resultRepository, times(1)).findAll();
    }

    @Test
    public void testGetResult() {
        Result result = new Result();
        result.setId(1);
        when(resultRepository.findById(1)).thenReturn(Optional.of(result));

        Result foundResult = resultService.getResult(1);

        assertEquals(1, foundResult.getId());
        verify(resultRepository, times(1)).findById(1);
    }

    @Test
    public void testGetResultNotFound() {
        when(resultRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> resultService.getResult(1));
        verify(resultRepository, times(1)).findById(1);
    }

    @Test
    public void testCreateResult() {
        Result result = new Result();
        when(resultRepository.save(result)).thenReturn(result);

        Result createdResult = resultService.createResult(result);

        assertEquals(result, createdResult);
        verify(resultRepository, times(1)).save(result);
    }

    @Test
    public void testUpdateResult() {
        Result result = new Result();
        result.setId(1);
        when(resultRepository.existsById(1)).thenReturn(true);
        when(resultRepository.save(result)).thenReturn(result);

        Result updatedResult = resultService.updateResult(1, result);

        assertEquals(result, updatedResult);
        verify(resultRepository, times(1)).existsById(1);
        verify(resultRepository, times(1)).save(result);
    }

    @Test
    public void testUpdateResultNotFound() {
        Result result = new Result();
        result.setId(1);
        when(resultRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> resultService.updateResult(1, result));
        verify(resultRepository, times(1)).existsById(1);
    }
    

    @Test
    public void testDeleteResultNotFound() {
        when(resultRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> resultService.deleteResult(1));
        verify(resultRepository, times(1)).existsById(1);
    }
}
