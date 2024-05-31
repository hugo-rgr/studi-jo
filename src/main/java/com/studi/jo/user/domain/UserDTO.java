package com.studi.jo.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
                   @JsonProperty("password") @Valid @NotNull @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*+-]).{8,20}$",
                           message = "Password length should be 8-20 characters and should include at least one upper case letter, one lower case letter, one digit, and one special character.") String rawPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = new Password(rawPassword);
    }

    public User toUser(Role role) {
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
