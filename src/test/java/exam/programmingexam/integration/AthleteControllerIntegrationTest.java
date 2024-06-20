package exam.programmingexam.integration;

import exam.programmingexam.athlete.Athlete;
import exam.programmingexam.athlete.AthleteService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AthleteControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private AthleteService athleteService;

    private Athlete athlete1;
    private Athlete athlete2;

    @BeforeEach
    public void setup() {
        athlete1 = new Athlete(1, "Athlete One", "Male", 25, "Club A", null, null);
        athlete2 = new Athlete(2, "Athlete Two", "Female", 30, "Club B", null, null);

        when(athleteService.getAthlete(1)).thenReturn(athlete1);
        when(athleteService.getAthlete(2)).thenReturn(athlete2);

        List<Athlete> allAthletes = Arrays.asList(athlete1, athlete2);
        when(athleteService.getAthletes()).thenReturn(allAthletes);

        when(athleteService.createAthlete(any(Athlete.class))).thenAnswer(invocation -> {
            Athlete newAthlete = invocation.getArgument(0);
            newAthlete.setId(3);
            return newAthlete;
        });

        when(athleteService.updateAthlete(eq(2), any(Athlete.class))).thenAnswer(invocation -> {
            Athlete updatedAthlete = invocation.getArgument(1);
            updatedAthlete.setId(2);
            return updatedAthlete;
        });

        when(athleteService.deleteAthlete(1)).thenReturn("Athlete deleted successfully");
    }

    @Test
    public void testGetAthleteById() {
        ResponseEntity<Athlete> response = restTemplate.getForEntity("/athletes/1", Athlete.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(athlete1.getId(), response.getBody().getId());
        assertEquals(athlete1.getName(), response.getBody().getName());
    }

    @Test
    public void testGetAllAthletes() {
        ResponseEntity<Athlete[]> response = restTemplate.getForEntity("/athletes", Athlete[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Athlete[] athletes = response.getBody();
        assertEquals(2, athletes.length);
        assertEquals(athlete1.getId(), athletes[0].getId());
        assertEquals(athlete2.getId(), athletes[1].getId());
    }

    @Test
    public void testCreateAthlete() {
        Athlete newAthlete = new Athlete("New Athlete", "Male", 28, "Club C", null, null);

        ResponseEntity<Athlete> response = restTemplate.postForEntity("/athletes", newAthlete, Athlete.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(newAthlete.getName(), response.getBody().getName());
        assertEquals(newAthlete.getGender(), response.getBody().getGender());
        assertEquals(newAthlete.getAge(), response.getBody().getAge());
        assertEquals(newAthlete.getClub(), response.getBody().getClub());

    }

    @Test
    public void testUpdateAthlete() {
        Athlete updatedAthlete = new Athlete(2, "Updated Athlete", "Female", 28, "Club B", null, null);
        ResponseEntity<Athlete> response = restTemplate.exchange("/athletes/2", HttpMethod.PUT, new HttpEntity<>(updatedAthlete), Athlete.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getId());
        assertEquals("Updated Athlete", response.getBody().getName());
    }

    @Test
    public void testDeleteAthlete() {
        ResponseEntity<String> response = restTemplate.exchange("/athletes/1", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Athlete deleted successfully", response.getBody());
        verify(athleteService, times(1)).deleteAthlete(1);
    }
}
