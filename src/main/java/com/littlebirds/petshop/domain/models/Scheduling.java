package com.littlebirds.petshop.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedulings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    private LocalDateTime date;

    public Scheduling(Pet pet, Worker worker, @NotNull @Future LocalDateTime date) {
        this.pet = pet;
        this.worker = worker;
        this.date = date;
    }
}
