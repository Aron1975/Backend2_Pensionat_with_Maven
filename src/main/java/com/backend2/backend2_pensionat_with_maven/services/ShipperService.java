package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.backend2.backend2_pensionat_with_maven.models.Shipper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface ShipperService {

    public List<Shipper> getAllShippers();
    public void sparaShipper(ShipperDto shipperDto);
    public Shipper shipperDtoToShipper(ShipperDto shipperDto);
    //public void addUpdateShipper(List<ShipperDto> shipperDtoList);
    public void addUpdateShipper() throws IOException;
    public List<ShipperDto> fetchShippers() throws IOException;
}
