package com.eml.hstfll.features.user.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "encoded_password")
    private String encodedPassword;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public UserType type;

    public static enum UserType {
        GUEST,
        HOST;

        public class MapValue {
            public static final String GUEST = "GUEST";
            public static final String HOST = "HOST";
        }
    }
}
