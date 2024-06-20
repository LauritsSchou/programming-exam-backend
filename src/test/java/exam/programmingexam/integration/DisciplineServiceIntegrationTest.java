package exam.programmingexam.integration;

import exam.programmingexam.discipline.Discipline;
import exam.programmingexam.discipline.DisciplineRepository;
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

public class DisciplineServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Test
    public void testGetDisciplines() {
        ResponseEntity<Discipline[]> response = restTemplate.getForEntity("/disciplines", Discipline[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetDiscipline() {
        Discipline discipline = new Discipline();
        discipline.setName("Test Discipline");
        discipline = disciplineRepository.save(discipline);

        ResponseEntity<Discipline> response = restTemplate.getForEntity("/disciplines/" + discipline.getId(), Discipline.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Discipline");
    }

    @Test
    public void testCreateDiscipline() {
        Discipline newDiscipline = new Discipline();
        newDiscipline.setName("New Discipline");
        ResponseEntity<Discipline> response = restTemplate.postForEntity("/disciplines", newDiscipline, Discipline.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("New Discipline");
    }

    @Test
    public void testUpdateDiscipline() {
        Discipline discipline = new Discipline();
        discipline.setName("Test Discipline");
        discipline = disciplineRepository.save(discipline);

        discipline.setName("Updated Discipline");
        restTemplate.put("/disciplines/" + discipline.getId(), discipline);

        Discipline updatedDiscipline = disciplineRepository.findById(discipline.getId()).orElse(null);
        assertThat(updatedDiscipline).isNotNull();
        assertThat(updatedDiscipline.getName()).isEqualTo("Updated Discipline");
    }

    @Test
    public void testDeleteDiscipline() {
        Discipline discipline = new Discipline();
        discipline.setName("Test Discipline");
        discipline = disciplineRepository.save(discipline);

        restTemplate.delete("/disciplines/" + discipline.getId());

        Discipline deletedDiscipline = disciplineRepository.findById(discipline.getId()).orElse(null);
        assertThat(deletedDiscipline).isNull();
    }}