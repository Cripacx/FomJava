package de.cripacx.fomjava.model;

import de.cripacx.fomjava.entity.User;
import de.cripacx.fomjava.util.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
public class UserResponseModel extends AbstractResponse {

    private String name;
    private String mail;

    public static UserResponseModel fromUser(final User user) {
        return UserResponseModel.builder().name(user.getName()).mail(user.getMail()).build();
    }

}
