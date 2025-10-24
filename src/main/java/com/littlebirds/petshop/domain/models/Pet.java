package com.littlebirds.petshop.domain.models;

import com.littlebirds.petshop.domain.dtos.pet.PetRegisterDto;
import com.littlebirds.petshop.domain.enums.PetType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // cria a FK em pets
    private Client client;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Column
    private String race;


    public Pet(PetRegisterDto pet,Client client) {
        this.client = client;
        this.name = pet.name();
        this.petType = pet.petType();
        this.race = pet.race();
    }
}
