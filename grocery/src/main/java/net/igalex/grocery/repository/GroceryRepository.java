package net.igalex.grocery.repository;

import net.igalex.grocery.model.GroceryItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends ReactiveCrudRepository<GroceryItem, Long> {

}
