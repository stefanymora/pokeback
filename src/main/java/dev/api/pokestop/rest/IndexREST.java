package dev.api.pokestop.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexREST {

    @GetMapping
    public String index() {
        return "BIENVENIDO A LA FUCKING POKESTOP! TU BACK FUNCIONA AL PEDAL. SIN ERROR 404";
    }


}
