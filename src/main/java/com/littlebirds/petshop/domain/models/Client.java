package com.littlebirds.petshop.domain.models;

import com.littlebirds.petshop.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    public void addPet(Pet pet)
    {
        this.pets.add(pet);
    }
    public void removePet(Pet pet)
    {
        this.pets.remove(pet);
    }

    public Client(String fullName, String email, String password, String phone, UserRole role) {
        super(fullName, email, password, phone, role);
    }

}
