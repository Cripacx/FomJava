package de.cripacx.fomjava.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DocumentConfig {

    @JsonProperty
    private String endpoint;
    @JsonProperty
    private String apiKey;
}
