package de.cripacx.fomjava;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisAsyncClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.theokanning.openai.service.OpenAiService;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FomJavaApplication {

    @Getter
    private static final Gson gson = new GsonBuilder().create();

    @Getter
    private static final DocumentAnalysisClient client = new DocumentAnalysisClientBuilder()
            .endpoint("https://fomproject-form.cognitiveservices.azure.com/")
            .credential(new AzureKeyCredential("2dc781123cc24614a795c049025fba1c")).buildClient();

    @Getter
    private static final OpenAiService openAiService = new OpenAiService("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");

    public static void main(String[] args) {
        SpringApplication.run(FomJavaApplication.class, args);
    }

}
