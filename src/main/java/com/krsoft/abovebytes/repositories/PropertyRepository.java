package com.krsoft.abovebytes.repositories;

import com.krsoft.abovebytes.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    Optional<Property> findByName(String propertyName);
}
