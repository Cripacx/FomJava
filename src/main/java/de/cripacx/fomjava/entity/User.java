package de.cripacx.fomjava.entity;

import de.cripacx.fomjava.permission.Permission;
import de.cripacx.fomjava.util.RandomString;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document
public class User {

    @Id @Getter
    private UUID id;

    private String name;
    @Indexed(unique = true)
    private String mail;
    private String password;
    private String sessionToken;
    private List<Permission> permissions = new ArrayList<>();

    public User(final UUID uuid, final String name, final String mail, final String password) {
        this.id = uuid;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.permissions = new ArrayList<>();
        this.generateSessionToken();
    }

    public String getAuthHeader() {
        return this.id.toString() + ":" + this.sessionToken;
    }

    public final void generateSessionToken() {
        this.sessionToken = new RandomString(64, new SecureRandom(), RandomString.alphanum).nextString();
    }
}
