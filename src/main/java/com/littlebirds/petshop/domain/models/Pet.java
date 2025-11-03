package com.littlebirds.petshop.domain.models;

import com.littlebirds.petshop.domain.dtos.pet.EditPetInfoDto;
import com.littlebirds.petshop.domain.dtos.pet.PetRegisterDto;
import com.littlebirds.petshop.domain.enums.PetType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "user_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scheduling> schedulings = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Column(nullable = false)
    private String race;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private LocalDate born;

    public Pet(PetRegisterDto pet,Client client) {
        this.client = client;
        this.name = pet.name();
        this.petType = pet.petType();
        this.race = pet.race();
        this.color = pet.color();
        this.born = pet.born();
    }

    public void editPetInfo(EditPetInfoDto petInfoDto)
    {
        if(petInfoDto.name()!=null)
        {
            this.name = petInfoDto.name();
        }
        if(petInfoDto.race()!=null)
        {
            this.race = petInfoDto.race();
        }
        if(petInfoDto.color()!=null)
        {
            this.color = petInfoDto.color();
        }
        if(petInfoDto.born()!=null)
        {
            this.born = petInfoDto.born();
        }
    }
    public void addScheduling(Scheduling scheduling) {
        schedulings.add(scheduling);
        scheduling.setPet(this);
    }

    public void removeScheduling(Scheduling scheduling) {
        schedulings.remove(scheduling);
        scheduling.setPet(null);
    }
}
