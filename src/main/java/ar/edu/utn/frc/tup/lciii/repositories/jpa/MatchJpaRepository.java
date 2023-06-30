package ar.edu.utn.frc.tup.lciii.repositories.jpa;

import ar.edu.utn.frc.tup.lciii.entities.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchJpaRepository extends JpaRepository<MatchEntity, Long> {

    @Query("SELECT me FROM MatchEntity me WHERE me.playerOne.id = :playerId OR me.playerTwo.id = :playerId")
    Optional<List<MatchEntity>> getAllByPlayerOneOrPlayerTwo(Long playerId);
}
