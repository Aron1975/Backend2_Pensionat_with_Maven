package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipper {

    @Id
    @GeneratedValue
    public int id;
    public int shipperId;
    //public String email;
    public String companyName;
   // public String contactName;
   // public String contactTitle;
   // public String streetAddress;
   // public String city;
   // public String postalCode;
   // public String country;
    public String phone;
   // public String fax;
}
