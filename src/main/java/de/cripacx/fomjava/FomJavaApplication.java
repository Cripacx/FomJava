package de.cripacx.fomjava;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisAsyncClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.theokanning.openai.service.OpenAiService;
import de.cripacx.fomjava.config.Config;
import de.cripacx.fomjava.config.DocumentConfig;
import de.cripacx.fomjava.config.OpenAIConfig;
import de.cripacx.fomjava.exception.FomException;
import de.cripacx.fomjava.util.AzureOpenAI;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

@SpringBootApplication
public class FomJavaApplication {

    @Getter
    private static final Gson gson = new GsonBuilder().create();

    @Getter
    private static Config config;

    @Getter
    private static DocumentAnalysisClient client;

    @Getter
    private static AzureOpenAI azureOpenAI;

    public static void main(String[] args) {
        loadConfig();
        SpringApplication.run(FomJavaApplication.class, args);
    }

    private static void loadConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("config.json");
        if(!file.exists()) {
            createFile();
            exit(0);
        }

        try {
            // Read from JSON file
            config = objectMapper.readValue(file, Config.class);
            client = new DocumentAnalysisClientBuilder()
                    .endpoint(config.getDocumentConfig().getEndpoint())
                    .credential(new AzureKeyCredential(config.getDocumentConfig().getApiKey())).buildClient();
            azureOpenAI = new AzureOpenAI(config.getOpenAiConfig().getEndpoint(), config.getOpenAiConfig().getApiKey());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            config = new Config();

            config.setDocumentConfig(new DocumentConfig());
            config.setOpenAiConfig(new OpenAIConfig());

            // Write to JSON file
            objectMapper.writeValue(new File("config.json"), config);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
