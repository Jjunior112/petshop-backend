package com.littlebirds.petshop.domain.dtos.address;

import com.littlebirds.petshop.domain.models.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressListDto(
        String street,
        String neighborhood,
        String zipCode,
        String city,
        String state,
        String complement,
        String number
) {
    public AddressListDto(Address address) {
        this(
                address.getStreet(),
                address.getNeighborhood(),
                address.getZipCode(),
                address.getCity(),
                address.getState(),
                address.getComplement(),
                address.getNumber()
        );
    }
}
