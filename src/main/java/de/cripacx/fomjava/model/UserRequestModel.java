package de.cripacx.fomjava.model;

import de.cripacx.fomjava.FomJavaApplication;
import de.cripacx.fomjava.exception.BadRequestException;
import lombok.Getter;

@Getter
public class UserRequestModel {

    private String mail;
    private String password;
    private String name;

    public static UserRequestModel fromJson(String json) throws BadRequestException {
        try{
            return FomJavaApplication.getGson().fromJson(json, UserRequestModel.class);
        }catch (Exception e){
            throw new BadRequestException();
        }
    }

}
