package exam.programmingexam.integration;

import exam.programmingexam.discipline.Discipline;
import exam.programmingexam.discipline.DisciplineService;
import exam.programmingexam.enums.ResultType;
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
public class DisciplineControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private DisciplineService disciplineService;

    private Discipline discipline1;
    private Discipline discipline2;

    @BeforeEach
    public void setup() {
        discipline1 = new Discipline(1, "Discipline One", ResultType.TIME, null);
        discipline2 = new Discipline(2, "Discipline Two", ResultType.DISTANCE, null);

        when(disciplineService.getDiscipline(1)).thenReturn(discipline1);
        when(disciplineService.getDiscipline(2)).thenReturn(discipline2);

        List<Discipline> allDisciplines = Arrays.asList(discipline1, discipline2);
        when(disciplineService.getDisciplines()).thenReturn(allDisciplines);

        when(disciplineService.createDiscipline(any(Discipline.class))).thenAnswer(invocation -> {
            Discipline newDiscipline = invocation.getArgument(0);
            newDiscipline.setId(3);
            return newDiscipline;
        });

        when(disciplineService.updateDiscipline(eq(2), any(Discipline.class))).thenAnswer(invocation -> {
            Discipline updatedDiscipline = invocation.getArgument(1);
            updatedDiscipline.setId(2); // Ensure ID remains the same
            return updatedDiscipline;
        });

        when(disciplineService.deleteDiscipline(1)).thenReturn("Discipline deleted successfully");
    }

    @Test
    public void testGetDisciplineById() {
        ResponseEntity<Discipline> response = restTemplate.getForEntity("/disciplines/1", Discipline.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(discipline1.getId(), response.getBody().getId());
        assertEquals(discipline1.getName(), response.getBody().getName());
    }

    @Test
    public void testGetAllDisciplines() {
        ResponseEntity<Discipline[]> response = restTemplate.getForEntity("/disciplines", Discipline[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Discipline[] disciplines = response.getBody();
        assertEquals(2, disciplines.length);
        assertEquals(discipline1.getId(), disciplines[0].getId());
        assertEquals(discipline2.getId(), disciplines[1].getId());
    }

    @Test
    public void testCreateDiscipline() {
        Discipline newDiscipline = new Discipline("New Discipline", ResultType.TIME, null);

        ResponseEntity<Discipline> response = restTemplate.postForEntity("/disciplines", newDiscipline, Discipline.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(newDiscipline.getName(), response.getBody().getName());
    }

    @Test
    public void testUpdateDiscipline() {
        Discipline updatedDiscipline = new Discipline(2, "Updated Discipline", ResultType.DISTANCE, null);
        ResponseEntity<Discipline> response = restTemplate.exchange("/disciplines/2", HttpMethod.PUT, new HttpEntity<>(updatedDiscipline), Discipline.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getId());
        assertEquals("Updated Discipline", response.getBody().getName());
    }

    @Test
    public void testDeleteDiscipline() {
        ResponseEntity<String> response = restTemplate.exchange("/disciplines/1", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Discipline deleted successfully", response.getBody());
        verify(disciplineService, times(1)).deleteDiscipline(1);
    }
}
