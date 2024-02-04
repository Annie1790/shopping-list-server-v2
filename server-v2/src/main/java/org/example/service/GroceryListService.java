package org.example.service;

import org.example.exception.IngredientNotFoundException;
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
                .switchIfEmpty(Mono.error(new IngredientNotFoundException())) //todo: aniko add GroceryItemNotFoundException
                .map(oldItem -> {
                    oldItem.setName(item.getName());
                    oldItem.setCompleted(item.isCompleted());
                    return oldItem;
                })
                .flatMap(groceryRepository::save);
    }

    public Mono<GroceryItem> findIngredientById(Long id) {
        return groceryRepository.findById(id);
    }

    public Mono<Void> deleteGroceryItem(Long id) {
        return groceryRepository.findById(id)
                .switchIfEmpty(Mono.error(new IngredientNotFoundException()))
                .flatMap(result -> {
                            return groceryRepository.deleteById(result.getId());
                        }
                );
    }
}
