package com.littlebirds.petshop.domain.dtos.user;

import com.littlebirds.petshop.domain.dtos.address.AddressRegisterDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record UserRegisterDto(
        @NotNull
                             @NotBlank
                              String fullName,
        @NotNull
                             @NotBlank
                             @Email
                              String email,
        @NotNull
                             @NotBlank
                             @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
                              String password,
                              String phone,
        @Valid
                              AddressRegisterDto address
) {
}
