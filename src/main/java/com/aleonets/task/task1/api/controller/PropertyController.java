package com.aleonets.task.task1.api.controller;

import com.aleonets.task.task1.api.model.Property;
import com.aleonets.task.task1.service.PropertyService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PropertyController {

	PropertyService propertyService;

	@GetMapping
	public List<Property> findAll() {
		return propertyService.findAllProperties();
	}
}
