package com.aleonets.task.task1.service;

import com.aleonets.task.task1.api.model.Property;
import com.aleonets.task.task1.storage.mapping.PropertyMapping;
import com.aleonets.task.task1.storage.repository.PropertyRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyService {

	PropertyRepository propertyRepository;
	PropertyMapping propertyMapping;

	public List<Property> findAllProperties() {
		return propertyMapping.toModel(propertyRepository.findAll());
	}
}
