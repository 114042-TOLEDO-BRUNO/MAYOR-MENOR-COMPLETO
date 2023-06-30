package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.models.Card;
import ar.edu.utn.frc.tup.lciii.models.CardSuit;
import ar.edu.utn.frc.tup.lciii.models.Deck;
import ar.edu.utn.frc.tup.lciii.services.DeckService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
@Service
public class DeckServiceImpl implements DeckService {

    @Override
    public Deck createDeck() {
        Deck deck = new Deck(new ArrayList<>());
        for(CardSuit cardSuit :CardSuit.values()) {
            for(int i = 1; i < 13; i++) {
                deck.getCards().add(new Card(cardSuit, i, BigDecimal.valueOf(i)));
            }
        }
        return deck;
    }

    @Override
    public void shuffleDeck(Deck deck) {
        //TO DO: Tomar el mazo (deck) que viene como parametro y mezclar las cartas
        Collections.shuffle(deck.getCards());
    }

    @Override
    public Card takeCard(Deck deck, Integer deckIndex) {
        //TO DO: Tomar del mazo (deck) que viene como parametro la carta de la posición indicada en el parametro deckIndex

        return deck.getCards().get(deckIndex);
    }
}
