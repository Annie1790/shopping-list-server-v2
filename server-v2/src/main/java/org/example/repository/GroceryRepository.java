package org.example.repository;

import org.example.model.GroceryItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends ReactiveCrudRepository<GroceryItem, Long> {

}
