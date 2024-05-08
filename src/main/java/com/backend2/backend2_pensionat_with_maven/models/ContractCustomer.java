package com.backend2.backend2_pensionat_with_maven.models;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
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
public class ContractCustomer {

  @Id
  @GeneratedValue
  public int id;
  private int costumerId;
  public String companyName;
  public String contactName;
  public String contactTitle;
  public String streetAddress;
  public String city;
  public int postalCode;
  public String country;
  public String phone;
  public String fax;
}
