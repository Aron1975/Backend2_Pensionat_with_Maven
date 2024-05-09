package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.backend2.backend2_pensionat_with_maven.models.Shipper;
import com.backend2.backend2_pensionat_with_maven.repos.ShipperRepo;
import com.backend2.backend2_pensionat_with_maven.services.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepo shipperRepo;

    @Override
    public void sparaShipper(ShipperDto shipperDto) {

        shipperRepo.save(shipperDtoToShipper(shipperDto));
    }

    @Override
    public Shipper shipperDtoToShipper(ShipperDto shipperDto) {
        Shipper shipper = new Shipper();
        shipper.setShipperId(shipperDto.getId());
        //shipper.setEmail(shipperDto.getEmail());
        shipper.setCompanyName(shipperDto.getCompanyName());
       // shipper.setContactName(shipperDto.getContactName());
       // shipper.setContactTitle(shipperDto.getContactTitle());
       // shipper.setStreetAddress(shipperDto.getStreetAddress());
      //  shipper.setCity(shipperDto.getCity());
      //  shipper.setPostalCode(shipperDto.getPostalCode());
       // shipper.setCountry(shipperDto.getCountry());
        shipper.setPhone(shipperDto.getPhone());
       // shipper.setFax(shipperDto.getFax());
        return shipper;
    }

    @Override
    public void addUpdateShipper(ShipperDto shipperDto) {
        sparaShipper(shipperDto);
    }
}
