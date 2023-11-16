package com.eml.hstfll.features.property.domain.entities;

import com.eml.hstfll.features.user.domain.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "property")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @ManyToOne()
    private UserEntity hostUser;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;
}
