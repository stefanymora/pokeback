package dev.api.pokestop.rest;

import dev.api.pokestop.entity.Card;
import dev.api.pokestop.service.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@CrossOrigin(origins = {
        "https://pokestop-app.vercel.app",
        "http://localhost:3000"
})
public class CardsREST {

    @Autowired
    private CardServiceImpl cardServiceImpl;

    @GetMapping
    public ResponseEntity<List<Card>> getAllProducts() {
        List<Card> cards = cardServiceImpl.getAllCards();
        return ResponseEntity.ok(cards);
    }


    @GetMapping("/{cardName}")
    public ResponseEntity<Card> getCardByName(@PathVariable String cardName) {
        Card card = cardServiceImpl.getCard(cardName);

        if (card != null) {
            return ResponseEntity.ok(card);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCard(@PathVariable String id, @RequestBody Card updateCard) {
        String result = cardServiceImpl.updateCard(id, updateCard);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> newCard (@RequestBody Card newCard) {
        if (newCard.getName() == null || newCard.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre de la carta obligatoria.");
        }

        String cardMessage = cardServiceImpl.saveCard(newCard);
        return ResponseEntity.ok(cardMessage);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable String id) {
        String result = cardServiceImpl.deleteCard(id);
       return  ResponseEntity.ok(result);
    }


}
