package net.igalex.grocery.controller;

import net.igalex.grocery.model.GroceryItem;
import net.igalex.grocery.repository.GroceryRepository;
import net.igalex.grocery.service.GroceryListService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest
//@Import(GroceryListService.class)
//Either use Import or ComponentScan -> CS is more like a convention, Import looks like a configuration
public class GroceryListControllerTest {

    private final String ALL_ITEM_URL = "/routes/shopItem/all";
    private final String COMPLETED_URL = "/routes/shopItem/completed";
    private final String UNCOMPLETED_URL = "/routes/shopItem/uncompleted";
    private final String NEW_URL = "/routes/shopItem/new";
    private final String UPDATE_URL = "/routes/shopItem/update";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GroceryListService service;

    @MockBean
    private GroceryRepository repository;

    GroceryItem banana = GroceryItem.builder()
            .name("banana")
            .isCompleted(true)
            .build();

    GroceryItem cheese = GroceryItem.builder()
            .name("cheese")
            .isCompleted(false)
            .build();

    @Test
    void shouldReturnAllItems() throws JSONException {
        Mockito.when(service.getGroceryList()).thenReturn(Flux.just(banana, cheese));

        JSONObject bananaJson = new JSONObject();
        bananaJson.put("name", banana.getName());
        bananaJson.put("isCompleted", banana.getIsCompleted());

        JSONObject cheeseJson = new JSONObject();
        cheeseJson.put("name", cheese.getName());
        cheeseJson.put("isCompleted", cheese.getIsCompleted());

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(bananaJson);
        jsonArray.put(cheeseJson);


        webTestClient
                .get()
                .uri(ALL_ITEM_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .json(String.valueOf(jsonArray));

    }

    @Test
    void shouldReturnListWithCompletedItems() throws JSONException {
        Mockito.when(service.getGroceryListByStatus(true)).thenReturn(Flux.just(banana));

        JSONObject bananaJson = new JSONObject();
        bananaJson.put("name", banana.getName());
        bananaJson.put("isCompleted", banana.getIsCompleted());

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(bananaJson);

        webTestClient
                .get()
                .uri(COMPLETED_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .json(String.valueOf(jsonArray));

    }

    @Test
    void shouldReturnListWithUncompletedItems() throws JSONException {
        Mockito.when(service.getGroceryListByStatus(false)).thenReturn(Flux.just(cheese));

        JSONObject bananaJson = new JSONObject();
        bananaJson.put("name", cheese.getName());
        bananaJson.put("isCompleted", cheese.getIsCompleted());

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(bananaJson);

        webTestClient
                .get()
                .uri(UNCOMPLETED_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .json(String.valueOf(jsonArray));

    }

    @Test
    void shouldSaveNewItem() {

        webTestClient.post()
                .uri(NEW_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cheese))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(service, times(1)).addGroceryItem(cheese);
    }

//    @Test
//    //todo: aniko fix broken test
//    void shouldUpdateItem() {
//        GroceryItem update = GroceryItem.builder()
//                .name("cheddar")
//                .isCompleted(false)
//                .build();
//
//        webTestClient.put()
//                .uri(UPDATE_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(update))
//                .exchange();
//
//        Mockito.verify(service, times(1)).updateGroceryItem(cheese);
//    }
}
