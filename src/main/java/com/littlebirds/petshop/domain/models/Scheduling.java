package com.littlebirds.petshop.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.littlebirds.petshop.domain.enums.SchedulingStatus;
import com.littlebirds.petshop.domain.enums.ServiceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    private String clientName;

    private String clientEmail;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SchedulingStatus status = SchedulingStatus.PENDING;

    public Scheduling(Pet pet,ServiceType serviceType, String clientName, String clientEmail, Worker worker, @NotNull @Future OffsetDateTime date) {
        this.pet = pet;
        this.serviceType = serviceType;
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
