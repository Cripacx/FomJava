package de.cripacx.fomjava.model;

import de.cripacx.fomjava.entity.Ingredient;
import de.cripacx.fomjava.entity.Recipe;
import de.cripacx.fomjava.util.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor @Builder
public class RecipeResponseModel extends AbstractResponse {

    private UUID id;
    private UUID creator;
    private String name;
    private String image;
    @Setter
    private String description;
    private List<Ingredient> ingredients;

    public static RecipeResponseModel fromRecipe(Recipe recipe) {
        return new RecipeResponseModel(recipe.getId(), recipe.getCreator(), recipe.getName(), recipe.getImage(), recipe.getDescription(), recipe.getIngredients());
    }

    public static List<RecipeResponseModel> fromRecipes(List<Recipe> recipes) {
        List<RecipeResponseModel> recipeResponseModels = new ArrayList<>();
        recipes.forEach(recipe -> recipeResponseModels.add(fromRecipe(recipe)));
        return recipeResponseModels;
    }

}
