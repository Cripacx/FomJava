package de.cripacx.fomjava.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Config {

    @JsonProperty("Document")
    private DocumentConfig documentConfig;
    @JsonProperty("OpenAI")
    private OpenAIConfig openAiConfig;

}
