package com.krsoft.abovebytes.services.properties;

import com.krsoft.abovebytes.entities.Property;

import java.util.Optional;

public interface PropertyService {
    Optional<Property> getProperty(String name);
}
