package com.backend2.backend2_pensionat_with_maven.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetailsDto {

    private String to;
    private String subject;
    private String message;
    private String name;
}
