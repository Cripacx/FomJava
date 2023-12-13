package de.cripacx.fomjava.model;

import de.cripacx.fomjava.FomJavaApplication;
import de.cripacx.fomjava.entity.Ingredient;
import de.cripacx.fomjava.entity.Recipe;
import de.cripacx.fomjava.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class RecipeRequestModel {

    private UUID id;
    private String name;
    private String image;
    private String description;
    private List<Ingredient> ingredients;

    public static RecipeRequestModel fromJson(String json) throws BadRequestException {
        try{
            return FomJavaApplication.getGson().fromJson(json, RecipeRequestModel.class);
        }catch (Exception e){
            throw new BadRequestException();
        }
    }

    public Recipe.RecipeBuilder toRecipe() {
        return Recipe.builder().id(id).name(this.name).image(this.image).description(this.description).ingredients(this.ingredients);
    }

}
