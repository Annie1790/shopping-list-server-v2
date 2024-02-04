package org.example.controller;

import org.example.exception.IngredientNotFoundException;
import org.example.model.GroceryItem;
import org.example.service.GroceryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RestController
@RequestMapping("/routes/shopItem")
public class GroceryListController {

    @Autowired
    GroceryListService service;

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<GroceryItem> findGroceryList() {
        return service.getGroceryList();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveIngredient(@RequestBody GroceryItem item) {
        service.addGroceryItem(item);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<GroceryItem> updateIngredient(@RequestBody GroceryItem item) throws IngredientNotFoundException {
             return service.updateGroceryItem(item);

    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteIngredientById(@PathVariable String id) {
        final Long ingredientId = Long.valueOf(id);
        return service.deleteGroceryItem(ingredientId);
    }
}
