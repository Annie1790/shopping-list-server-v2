package net.igalex.grocery.exception;

public class IngredientNotFoundException extends Exception{
    public IngredientNotFoundException() {
        super("Ingredient not found");
    }
}
