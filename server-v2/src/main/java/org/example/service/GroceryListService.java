package org.example.service;

import org.example.model.GroceryListModel;
import org.example.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GroceryListService {
    @Autowired
    GroceryRepository groceryRepository;

    public Flux<GroceryListModel> getGroceryList() {return groceryRepository.findAll();};

    public void addGroceryItem(GroceryListModel item) {groceryRepository.save(item).subscribe();};

    //  todo: aniko finish getGroceryByStatus
    //  public Flux<GroceryListModel> getGroceryByStatus(boolean status) {return groceryRepository.}

    public Mono<GroceryListModel> updateGroceryItem(GroceryListModel item) {
        return groceryRepository.findById(item.getId())
                .switchIfEmpty(Mono.error(new Exception("User not found"))) //todo: aniko add GroceryItemNotFoundException
                .map(oldItem -> {
                    oldItem.setName(item.getName());
                    oldItem.setCompleted(item.isCompleted());
                })


    }
}
