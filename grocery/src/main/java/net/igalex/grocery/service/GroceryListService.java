package net.igalex.grocery.service;

import net.igalex.grocery.exception.IngredientNotFoundException;
import net.igalex.grocery.model.GroceryItem;
import net.igalex.grocery.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GroceryListService {
    @Autowired
    GroceryRepository groceryRepository;

    public Flux<GroceryItem> getGroceryList() {return groceryRepository.findAll();}

    public void addGroceryItem(GroceryItem item) {groceryRepository.save(item).subscribe();}

    public Flux<GroceryItem> getGroceryListByStatus(boolean status) {return groceryRepository.findAllByStatus(status);}

    public Mono<GroceryItem> updateGroceryItem(GroceryItem item) {
        return groceryRepository.findById(item.getId())
                .switchIfEmpty(Mono.error(new IngredientNotFoundException())) //todo: aniko add GroceryItemNotFoundException
                .map(oldItem -> {
                    oldItem.setName(item.getName());
                    oldItem.setIsCompleted(item.getIsCompleted());
                    return oldItem;
                })
                .flatMap(groceryRepository::save);
    }

    public Mono<GroceryItem> findGroceryById(Long id) {
        return groceryRepository.findById(id)
                .switchIfEmpty(Mono.error(new IngredientNotFoundException()));
    }

    public Mono<Void> deleteGroceryItem(Long id) {
        return groceryRepository.findById(id)
                .switchIfEmpty(Mono.error(new IngredientNotFoundException()))
                .flatMap(result -> groceryRepository.deleteById(result.getId()));
    }
}
