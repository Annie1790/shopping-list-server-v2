package org.example.repository;

import org.example.model.GroceryListModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
@org.springframework.stereotype.Repository
public interface GroceryRepository extends ReactiveCrudRepository<GroceryListModel, Long> {

}
