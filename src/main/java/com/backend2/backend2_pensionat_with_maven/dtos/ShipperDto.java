package com.backend2.backend2_pensionat_with_maven.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipperDto {

    public int id;
    public String email;
    public String companyName;
    public String contactName;
    public String contactTitle;
    public String streetAddress;
    public String city;
    public String postalCode;
    public String country;
    public String phone;
    public String fax;
}
