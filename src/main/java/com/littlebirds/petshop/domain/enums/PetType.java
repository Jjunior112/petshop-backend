package com.littlebirds.petshop.domain.enums;

public enum PetType {
    DOG("Cachorro"),
    CAT("Gato");
    private final String type;

    PetType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
