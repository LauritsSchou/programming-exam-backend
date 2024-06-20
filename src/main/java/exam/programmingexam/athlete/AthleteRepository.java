package exam.programmingexam.athlete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AthleteRepository extends JpaRepository<Athlete, Integer> {
    @Query("SELECT a FROM Athlete a JOIN a.results r WHERE r.id = :resultId")
    Athlete findByResultId(@Param("resultId") int id);
}
