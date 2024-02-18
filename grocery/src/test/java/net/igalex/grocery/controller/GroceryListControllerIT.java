package net.igalex.grocery.controller;

import net.igalex.grocery.model.GroceryItem;
import net.igalex.grocery.repository.GroceryRepository;
import net.igalex.grocery.service.GroceryListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ActiveProfiles("test")
//@Import(GroceryListService.class)
//Either use Import or ComponentScan -> CS is more like a convention, Import looks like a configuration
public class GroceryListControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GroceryListService service;

    @Autowired
    private GroceryRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll().block();
   }

    GroceryItem banana = GroceryItem.builder()
            .name("banana")
            .isCompleted(true)
            .build();

    GroceryItem cheese = GroceryItem.builder()
            .name("cheese")
            .isCompleted(false)
            .build();

    @Test
    void test() {

    }
}
