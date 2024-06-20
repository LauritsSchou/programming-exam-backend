package exam.programmingexam.integration;


import exam.programmingexam.athlete.Athlete;
import exam.programmingexam.athlete.AthleteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AthleteServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AthleteRepository athleteRepository;

    @Test
    public void testGetAthletes() {
        ResponseEntity<Athlete[]> response = restTemplate.getForEntity("/athletes", Athlete[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetAthlete() {
        Athlete athlete = new Athlete();
        athlete.setName("Test Athlete");
        athlete = athleteRepository.save(athlete);

        ResponseEntity<Athlete> response = restTemplate.getForEntity("/athletes/" + athlete.getId(), Athlete.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Athlete");
    }

    @Test
    public void testCreateAthlete() {
        Athlete newAthlete = new Athlete();
        newAthlete.setName("New Athlete");
        newAthlete.setAge(25);
        newAthlete.setGender("Male");
        newAthlete.setDisciplines(null);
        newAthlete.setResults(null);
        ResponseEntity<Athlete> response = restTemplate.postForEntity("/athletes", newAthlete, Athlete.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("New Athlete");
    }

    @Test
    public void testUpdateAthlete() {
        Athlete athlete = new Athlete();
        athlete.setName("Test Athlete");
        athlete = athleteRepository.save(athlete);

        athlete.setName("Updated Athlete");
        restTemplate.put("/athletes/" + athlete.getId(), athlete);

        Athlete updatedAthlete = athleteRepository.findById(athlete.getId()).orElse(null);
        assertThat(updatedAthlete).isNotNull();
        assertThat(updatedAthlete.getName()).isEqualTo("Updated Athlete");
    }

    @Test
    public void testDeleteAthlete() {
        Athlete athlete = new Athlete();
        athlete.setName("Test Athlete");
        athlete = athleteRepository.save(athlete);

        restTemplate.delete("/athletes/" + athlete.getId());

        Athlete deletedAthlete = athleteRepository.findById(athlete.getId()).orElse(null);
        assertThat(deletedAthlete).isNull();
    }}
