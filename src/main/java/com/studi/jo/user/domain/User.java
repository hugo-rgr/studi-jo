package com.studi.jo.user.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

// Utilisateurs

@Entity
@Table(name = "user_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FirstName firstName;

    @Embedded
    private LastName lastName;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private UserKey userKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
