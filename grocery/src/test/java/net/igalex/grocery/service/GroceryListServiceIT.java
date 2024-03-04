package net.igalex.grocery.service;


import net.igalex.grocery.exception.IngredientNotFoundException;
import net.igalex.grocery.model.GroceryItem;
import net.igalex.grocery.repository.GroceryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class GroceryListServiceIT {
    @Autowired
    GroceryListService service;

    @Autowired
    GroceryRepository repository;

    GroceryItem banana;

    GroceryItem cheese;

    @BeforeEach
    void setUp() {
        banana = repository.save(GroceryItem.builder()
                .name("banana")
                .isCompleted(true)
                .build()).block();
        cheese = repository.save(GroceryItem.builder()
                .name("cheese")
                .isCompleted(false)
                .build()).block();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll().block();
    }

    @Test
    void getGroceryListReturnsAValidList() {
        // when
        var items = service.getGroceryList().collectList().block();

        // then
        assertNotNull(items);
    }

    @Test
    void getGroceryListReturnsAllItems() {
        // when
        var items = service.getGroceryList().collectList().block();

        // then
        assertNotNull(items);
        assertEquals(2, items.size());

        items.sort((a,b) -> (int) (a.getId() - b.getId()));

        assertEquals(items.getFirst(), banana);
        assertEquals(items.getLast(), cheese);
    }

    @Test
    void getGroceryItemReturnExceptionIfIdNotExist() {
        StepVerifier.create(service.findGroceryById(3L))
                .expectError(IngredientNotFoundException.class)
                .verify();
    }

    @Test
    void getGroceryListByStatusReturnListWithCompletedStatus() {
        //when
        var items = service.getGroceryListByStatus(true).collectList().block();

        //then
        assertNotNull(items);
        assertEquals(1, items.size());
        assertTrue(items.getFirst().getIsCompleted());
    }
}
