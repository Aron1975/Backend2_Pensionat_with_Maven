package com.backend2.backend2_pensionat_with_maven.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlacklistDto {
    public int id;
    public String email;
    public String name;
    public String group;
    public Date created;
    public Boolean ok;
}
