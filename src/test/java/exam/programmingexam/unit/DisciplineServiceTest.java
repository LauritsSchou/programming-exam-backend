package exam.programmingexam.unit;

import exam.programmingexam.discipline.Discipline;
import exam.programmingexam.discipline.DisciplineRepository;
import exam.programmingexam.discipline.DisciplineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DisciplineServiceTest {
    private DisciplineRepository disciplineRepository;
    private DisciplineService disciplineService;

    private Discipline discipline1;
    private Discipline discipline2;

    @BeforeEach
    public void setup() {
        disciplineRepository = Mockito.mock(DisciplineRepository.class);
        disciplineService = new DisciplineService(disciplineRepository);

        discipline1 = new Discipline();
        discipline1.setId(1);
        discipline1.setName("Discipline 1");

        discipline2 = new Discipline();
        discipline2.setId(2);
        discipline2.setName("Discipline 2");
    }
    @Test
    public void testGetDisciplines() {
        when(disciplineRepository.findAll()).thenReturn(Arrays.asList(discipline1, discipline2));

        List<Discipline> disciplines = disciplineService.getDisciplines();

        assertEquals(2, disciplines.size());
        verify(disciplineRepository, times(1)).findAll();
    }

    @Test
    public void testGetDiscipline() {
        when(disciplineRepository.findById(1)).thenReturn(Optional.of(discipline1));

        Discipline discipline = disciplineService.getDiscipline(1);

        assertEquals(1, discipline.getId());
        verify(disciplineRepository, times(1)).findById(1);
    }

    @Test
    public void testGetDisciplineNotFound() {
        when(disciplineRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> disciplineService.getDiscipline(1));
        verify(disciplineRepository, times(1)).findById(1);
    }

    @Test
    public void testCreateDiscipline() {
        when(disciplineRepository.save(discipline1)).thenReturn(discipline1);

        Discipline discipline = disciplineService.createDiscipline(discipline1);

        assertEquals(1, discipline.getId());
        verify(disciplineRepository, times(1)).save(discipline1);
    }

    @Test
    public void testUpdateDiscipline() {
        when(disciplineRepository.existsById(1)).thenReturn(true);
        when(disciplineRepository.save(discipline1)).thenReturn(discipline1);

        Discipline discipline = disciplineService.updateDiscipline(1, discipline1);

        assertEquals(1, discipline.getId());
        verify(disciplineRepository, times(1)).existsById(1);
        verify(disciplineRepository, times(1)).save(discipline1);
    }

    @Test
    public void testUpdateDisciplineNotFound() {
        when(disciplineRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> disciplineService.updateDiscipline(1, discipline1));
        verify(disciplineRepository, times(1)).existsById(1);
    }

    @Test
    public void testDeleteDiscipline() {
        when(disciplineRepository.existsById(1)).thenReturn(true);

        String message = disciplineService.deleteDiscipline(1);

        assertEquals("Discipline deleted", message);
        verify(disciplineRepository, times(1)).existsById(1);
        verify(disciplineRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteDisciplineNotFound() {
        when(disciplineRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> disciplineService.deleteDiscipline(1));
        verify(disciplineRepository, times(1)).existsById(1);
    }
}