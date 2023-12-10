package de.cripacx.fomjava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FomJavaApplication {

    @Getter
    private static final Gson gson = new GsonBuilder().create();
    public static void main(String[] args) {
        SpringApplication.run(FomJavaApplication.class, args);
    }

}
