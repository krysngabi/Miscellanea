package com.krsoft.zedpay.services.properties.impl;

import com.krsoft.zedpay.entities.Property;
import com.krsoft.zedpay.repositories.PropertyRepository;
import com.krsoft.zedpay.services.properties.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Optional<Property> getProperty(String name) {
        Property property1 = null;
        Optional<Property> property = propertyRepository.findByName(name);

        if (property.isPresent()) {
            property1 = property.get();
        }

        return Optional.ofNullable(property1);
    }
}
