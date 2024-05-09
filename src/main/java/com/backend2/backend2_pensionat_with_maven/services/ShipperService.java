package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.backend2.backend2_pensionat_with_maven.models.Shipper;
import org.springframework.stereotype.Service;

public interface ShipperService {

    public void sparaShipper(ShipperDto shipperDto);
    public Shipper shipperDtoToShipper(ShipperDto shipperDto);
    public void addUpdateShipper(ShipperDto shipperDto);
}
