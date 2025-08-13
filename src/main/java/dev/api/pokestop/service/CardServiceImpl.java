package dev.api.pokestop.service;


import dev.api.pokestop.DAO.CardsDAO;
import dev.api.pokestop.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService{

    @Autowired
    private CardsDAO cardsDAO;

    @Override
    public String saveCard(Card card) {
        try {
            return cardsDAO.saveCard(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Card getCard(String card) {
        try {
            return cardsDAO.getCard(card);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String deleteCard(String card) {
        try {
            return cardsDAO.deleteCard(card);
        } catch (Exception e) {
            return "Error al eliminar el producto: " + e.getMessage();
        }    }

    @Override
    public List<Card> getAllCards() {
        try {
            return cardsDAO.getAll();
        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    @Override
    public String updateCard(String id, Card updateCard) {
        try {
            return cardsDAO.updateCard(id, updateCard);
        } catch (Exception e) {
            return "Error al actualizar el producto: " + e.getMessage();
        }
    }
}
