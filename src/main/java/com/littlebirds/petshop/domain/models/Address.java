package com.littlebirds.petshop.domain.models;

import com.littlebirds.petshop.domain.dtos.address.AddressRegisterDto;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;

    private String neighborhood;

    private String zipCode;

    private String city;

    private String state;

    private String complement;

    private String number;


    public Address(AddressRegisterDto data) {
        this.street = data.street();
        this.neighborhood = data.neighborhood();
        this.number = data.number();
        this.complement = data.complement();
        this.city = data.city();
        this.zipCode = data.zipCode();
        this.state = data.state();
    }

    public void updateInformation(AddressRegisterDto data) {

        if (data.street() != null) {
            this.street = data.street();
        }
        if (data.neighborhood() != null) {
            this.neighborhood = data.neighborhood();
        }
        if (data.number() != null) {
            this.number = data.number();
        }
        if (data.complement() != null) {
            this.complement = data.complement();
        }
        if (data.city() != null) {
            this.city = data.city();
        }
        if (data.zipCode() != null) {
            this.zipCode = data.zipCode();
        }
        if (data.state() != null) {
            this.state = data.state();
        }
    }
}
