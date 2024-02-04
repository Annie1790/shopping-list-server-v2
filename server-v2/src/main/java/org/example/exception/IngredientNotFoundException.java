package org.example.exception;

public class IngredientNotFoundException extends Exception{
    public IngredientNotFoundException() {
        super("Ingredient not found");
    }
}
