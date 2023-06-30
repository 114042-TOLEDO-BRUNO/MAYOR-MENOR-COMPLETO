package ar.edu.utn.frc.tup.lciii.entities;


import ar.edu.utn.frc.tup.lciii.models.Card;
import ar.edu.utn.frc.tup.lciii.models.Deck;
import ar.edu.utn.frc.tup.lciii.models.MatchStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matches")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Convert(converter = DeckConverter.class)
    @Column
    private Deck deck;

    @Convert(converter = CardConverter.class)
    @Column
    private Card lastCard;

    @Column
    private Integer nextCardIndex;

    @JoinColumn(name="player_one_id")
    @ManyToOne
    private PlayerEntity playerOne;



    @JoinColumn(name="player_two_id")
    @ManyToOne
    private PlayerEntity playerTwo;

    @JoinColumn(name="next_to_play_id")
    @ManyToOne
    private PlayerEntity nextToPlay;

    @JoinColumn(name="winner_id")
    @ManyToOne
    private PlayerEntity winner;

    @Column
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
}
