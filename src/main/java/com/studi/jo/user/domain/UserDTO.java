package com.studi.jo.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Value
public class UserDTO {

    @Valid
    @NotNull
    private FirstName firstName;

    @Valid
    @NotNull
    private LastName lastName;

    @Valid
    @NotNull
    private Email email;

    @Valid
    @NotNull
    private Password password;

    @JsonCreator
    public UserDTO(@JsonProperty("first_name") @Valid @NotNull FirstName firstName,
                   @JsonProperty("last_name") @Valid @NotNull LastName lastName,
                   @JsonProperty("email") @Valid @NotNull Email email,
                   @JsonProperty("password") @Valid @NotNull Password password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User toUser(Role role){
        return new User(
            null,
            this.firstName,
            this.lastName,
            this.email,
            this.password,
            new UserKey(),
            role
        );
    }
}
