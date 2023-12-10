package de.cripacx.fomjava.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Document
@Builder
public class Recipe {

    @Id
    private UUID id;

    private UUID creator;
    private String name;
    private String image;
    private String description;
    private List<Ingredient> ingredients;

}
