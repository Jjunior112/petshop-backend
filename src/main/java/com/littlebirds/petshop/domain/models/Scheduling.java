package com.littlebirds.petshop.domain.models;

import com.littlebirds.petshop.domain.enums.SchedulingStatus;
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

    private String clientName;

    private String clientEmail;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    private LocalDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SchedulingStatus status = SchedulingStatus.PENDING;

    public Scheduling(Pet pet,String clientName, String clientEmail, Worker worker, @NotNull @Future LocalDateTime date) {
        this.pet = pet;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.worker = worker;
        this.date = date;
    }

    public void changeStatus(SchedulingStatus status)
    {
        this.status = status;
    }
}
