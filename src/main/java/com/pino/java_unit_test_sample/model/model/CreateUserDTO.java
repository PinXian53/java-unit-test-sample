package com.pino.java_unit_test_sample.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDTO {
    private String identityNumber;
    private String name;
}
