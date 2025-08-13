package dev.api.pokestop.service;

import dev.api.pokestop.entity.Card;

import java.util.List;

public interface CardService {

    String saveCard(Card card) ;

    Card getCard(String card);

    String deleteCard(String card);

    List<Card> getAllCards();

    String updateCard(String id, Card updateCard);

}
