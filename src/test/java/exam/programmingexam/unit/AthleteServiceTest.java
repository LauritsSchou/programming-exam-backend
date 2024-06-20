package exam.programmingexam.unit;


import exam.programmingexam.athlete.Athlete;
import exam.programmingexam.athlete.AthleteRepository;
import exam.programmingexam.athlete.AthleteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AthleteServiceTest {
    private AthleteRepository athleteRepository;
    private AthleteService athleteService;

    private Athlete athlete1;
    private Athlete athlete2;

    @BeforeEach
    public void setup() {
        athleteRepository = Mockito.mock(AthleteRepository.class);
        athleteService = new AthleteService(athleteRepository);

        athlete1 = new Athlete();
        athlete1.setId(1);
        athlete1.setName("Athlete 1");

        athlete2 = new Athlete();
        athlete2.setId(2);
        athlete2.setName("Athlete 2");
    }
    @Test
    public void testGetAthletes() {
        when(athleteRepository.findAll()).thenReturn(Arrays.asList(athlete1, athlete2));

        List<Athlete> athletes = athleteService.getAthletes();

        assertEquals(2, athletes.size());
        verify(athleteRepository, times(1)).findAll();
    }

    @Test
    public void testGetAthlete() {
        when(athleteRepository.findById(1)).thenReturn(Optional.of(athlete1));

        Athlete athlete = athleteService.getAthlete(1);

        assertEquals(1, athlete.getId());
        verify(athleteRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAthleteNotFound() {
        when(athleteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> athleteService.getAthlete(1));
        verify(athleteRepository, times(1)).findById(1);
    }

    @Test
    public void testCreateAthlete() {
        when(athleteRepository.save(athlete1)).thenReturn(athlete1);

        Athlete athlete = athleteService.createAthlete(athlete1);

        assertEquals(1, athlete.getId());
        verify(athleteRepository, times(1)).save(athlete1);
    }

    @Test
    public void testUpdateAthlete() {
        when(athleteRepository.existsById(1)).thenReturn(true);
        when(athleteRepository.save(athlete1)).thenReturn(athlete1);

        Athlete athlete = athleteService.updateAthlete(1, athlete1);

        assertEquals(1, athlete.getId());
        verify(athleteRepository, times(1)).existsById(1);
        verify(athleteRepository, times(1)).save(athlete1);
    }

    @Test
    public void testUpdateAthleteNotFound() {
        when(athleteRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> athleteService.updateAthlete(1, athlete1));
        verify(athleteRepository, times(1)).existsById(1);
    }

    @Test
    public void testDeleteAthlete() {
        when(athleteRepository.existsById(1)).thenReturn(true);

        String message = athleteService.deleteAthlete(1);

        assertEquals("Athlete deleted", message);
        verify(athleteRepository, times(1)).existsById(1);
        verify(athleteRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAthleteNotFound() {
        when(athleteRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> athleteService.deleteAthlete(1));
        verify(athleteRepository, times(1)).existsById(1);
    }
}
