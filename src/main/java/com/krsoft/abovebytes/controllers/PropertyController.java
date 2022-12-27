package com.krsoft.abovebytes.controllers;

import com.krsoft.abovebytes.entities.Property;
import com.krsoft.abovebytes.services.properties.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;


@RestController
@RequestMapping(path = "/properties")
@CrossOrigin
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public HashMap<String, String> getProperties(@RequestParam(name = "name") String name) {
        HashMap<String,String> propertyDto = new HashMap<>();

        Optional<Property> property = propertyService.getProperty(name);

        propertyDto.put(name, property.map(Property::getValue).orElse(name + " not found"));

        return propertyDto;
    }
}
