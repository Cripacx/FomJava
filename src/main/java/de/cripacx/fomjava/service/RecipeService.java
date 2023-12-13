package de.cripacx.fomjava.service;

import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;
import com.azure.ai.formrecognizer.documentanalysis.models.DocumentLine;
import com.azure.ai.formrecognizer.documentanalysis.models.DocumentPage;
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult;
import com.azure.core.util.BinaryData;
import com.azure.core.util.polling.SyncPoller;
import de.cripacx.fomjava.FomJavaApplication;
import de.cripacx.fomjava.entity.Recipe;
import de.cripacx.fomjava.entity.User;
import de.cripacx.fomjava.exception.BadRequestException;
import de.cripacx.fomjava.exception.FomException;
import de.cripacx.fomjava.exception.recipe.RecipeNotFoundException;
import de.cripacx.fomjava.model.RecipeRequestModel;
import de.cripacx.fomjava.model.RecipeResponseModel;
import de.cripacx.fomjava.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    private UUID getNewId(){
        UUID uuid = UUID.randomUUID();
        while(this.recipeRepository.existsById(uuid))
            uuid = UUID.randomUUID();
        return uuid;
    }

    public ResponseEntity<String> getAll(User user) {
        this.recipeRepository.findAllByCreatorEquals(user.getId());
        return new ResponseEntity<String>("{\"recipes\":" + FomJavaApplication.getGson().toJson(RecipeResponseModel.fromRecipes(this.recipeRepository.findAllByCreatorEquals(user.getId()))) + "}", HttpStatus.OK);
    }

    public ResponseEntity<String> create(User user, RecipeRequestModel recipeRequestModel) {
        this.recipeRepository.save(new Recipe(
                this.getNewId(),
                user.getId(),
                recipeRequestModel.getName(),
                recipeRequestModel.getImage(),
                recipeRequestModel.getDescription(),
                recipeRequestModel.getIngredients()
        ));
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    public ResponseEntity<String> delete(User user, UUID id) throws FomException {
        Recipe recipe = this.recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
        if(!recipe.getCreator().equals(user.getId())) throw new RecipeNotFoundException();
        this.recipeRepository.delete(recipe);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    public ResponseEntity<String> put(User user, RecipeRequestModel recipeRequestModel) throws FomException {
        if(recipeRequestModel.getId() == null) return this.create(user, recipeRequestModel);
        Recipe recipe = this.recipeRepository.findById(recipeRequestModel.getId()).orElseThrow(RecipeNotFoundException::new);
        if(!recipe.getCreator().equals(user.getId())) throw new RecipeNotFoundException();
        this.recipeRepository.save(recipeRequestModel.toRecipe().creator(user.getId()).build());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    public ResponseEntity<String> uploadImage(User user, String base64Image) throws FomException {
        BinaryData data;
        System.out.println(base64Image);
        try {
            data = BinaryData.fromBytes(Base64.getDecoder().decode(base64Image));
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler");
            throw new BadRequestException();
        }
        SyncPoller<OperationResult, AnalyzeResult> analyzeDocumentPoller =
                FomJavaApplication.getClient().beginAnalyzeDocument("prebuilt-layout", data);
        AnalyzeResult analyzeResult = analyzeDocumentPoller.getFinalResult();

        for(DocumentPage page : analyzeResult.getPages()) {
            System.out.println("Page " + page.getPageNumber());
            for(DocumentLine line : page.getLines()) {
                System.out.println(line.getContent());
            }
        }
        System.out.println(analyzeResult.getContent());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
