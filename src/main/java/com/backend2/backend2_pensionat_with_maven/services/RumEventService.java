package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.RumEventDto;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;

public interface RumEventService {

    public String sparaRumEvent(RumEventDto rumEventDto);
}
