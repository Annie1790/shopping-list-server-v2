package org.example.service;

import org.example.model.GroceryItem;
import org.example.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GroceryListService {
    @Autowired
    GroceryRepository groceryRepository;

    public Flux<GroceryItem> getGroceryList() {return groceryRepository.findAll();};

    public void addGroceryItem(GroceryItem item) {groceryRepository.save(item).subscribe();};

    //  todo: aniko finish getGroceryByStatus
    //  public Flux<GroceryList> getGroceryByStatus(boolean status) {return groceryRepository.}

    public Mono<GroceryItem> updateGroceryItem(GroceryItem item) {
        return groceryRepository.findById(item.getId())
                .switchIfEmpty(Mono.error(new Exception("Ingredient not found"))) //todo: aniko add GroceryItemNotFoundException
                .map(oldItem -> {
                    oldItem.setName(item.getName());
                    oldItem.setCompleted(item.isCompleted());
                    return oldItem;
                })
                .flatMap(groceryRepository::save);
    }

    public GroceryItem findIngredientById(Long id) {
        return groceryRepository.findById(id).block();
    }

    public Mono<Void> deleteGroceryItem(GroceryItem item) {
        return groceryRepository.deleteById(item.getId())
                .switchIfEmpty(Mono.error(new Exception("Ingredient not found")));
    }
}
