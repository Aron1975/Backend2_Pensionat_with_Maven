package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.backend2.backend2_pensionat_with_maven.models.Shipper;
import com.backend2.backend2_pensionat_with_maven.repos.ShipperRepo;
import com.backend2.backend2_pensionat_with_maven.services.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepo shipperRepo;

    @Override
    public List<Shipper> getAllShippers() {
        return shipperRepo.findAll();
    }

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

    public int findIdByShipperId(int shipperId) {
        Shipper s = getAllShippers().stream().filter(shipper -> shipper.getShipperId() == shipperId).findFirst().orElse(null);
        if (s == null) return -1;
        return s.getId();
    }

    @Override
    public void addUpdateShipper(List<ShipperDto> shipperDtoList) {

        long startTime = System.nanoTime();

     /*   shipperRepo.deleteAll();
        for (ShipperDto sh : shipperDtoList) {
                sparaShipper(sh);
            }*/

        int tempId;
        for (ShipperDto sh : shipperDtoList) {
            if((tempId = findIdByShipperId(sh.getId())) == -1){
                sparaShipper(sh);
            }
            else{
                Shipper s = shipperRepo.findById(tempId).get();
                if((!s.companyName.equals(sh.getCompanyName())) || (!s.phone.equals(sh.getPhone()))){
                    updateShipper(tempId, sh);
                }

            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Duration: " + duration/1000000 + " ms");
    }

    public void updateShipper(int tempId, ShipperDto sh){
        //System.out.println(tempId + " " + sh.getCompanyName() + " " + sh.getPhone());
        shipperRepo.updateShipperById(sh.companyName, sh.phone, tempId);
    }
}
