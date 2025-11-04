package com.littlebirds.petshop.domain.models;

import com.littlebirds.petshop.domain.dtos.address.AddressRegisterDto;
import com.littlebirds.petshop.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")

public class Worker extends User {

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scheduling> schedulings = new ArrayList<>();

    public Worker(String fullName, String email, String password, String phone, UserRole role, AddressRegisterDto registerDto) {
        super(fullName, email, password, phone, role,registerDto);
    }

    public void addScheduling(Scheduling scheduling) {
        schedulings.add(scheduling);
        scheduling.setWorker(this);
    }

    public void removeScheduling(Scheduling scheduling) {
        schedulings.remove(scheduling);
        scheduling.setWorker(null);
    }

}
