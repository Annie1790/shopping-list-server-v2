package net.igalex.grocery.repository;

import net.igalex.grocery.model.GroceryItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GroceryRepository extends ReactiveCrudRepository<GroceryItem, Long> {

    @Query("SELECT * FROM grocery_list WHERE is_completed = $1")
    Flux<GroceryItem> findAllByStatus(Boolean status);
}
