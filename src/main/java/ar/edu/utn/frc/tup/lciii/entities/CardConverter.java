package ar.edu.utn.frc.tup.lciii.entities;

import ar.edu.utn.frc.tup.lciii.models.Card;
import ar.edu.utn.frc.tup.lciii.models.CardSuit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter
public class CardConverter implements AttributeConverter<Card, String> {
    @Override
    public String convertToDatabaseColumn(Card attribute) {
        return attribute.toString();
    }

    @Override
    public Card convertToEntityAttribute(String dbData) {
        String [] cardMap = dbData.split("_");
        return new Card(
                CardSuit.valueOf(cardMap[0]),
                Integer.valueOf(cardMap[1]),
                new BigDecimal(cardMap[2]));
    }
}
