package com.krsoft.zedpay.services.properties;

import com.krsoft.zedpay.entities.Property;

import java.util.Optional;

public interface PropertyService {
    Optional<Property> getProperty(String name);
}
