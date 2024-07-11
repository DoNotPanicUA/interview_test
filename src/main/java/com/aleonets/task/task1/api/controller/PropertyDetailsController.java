package com.aleonets.task.task1.api.controller;

import com.aleonets.task.task1.api.model.PropertyDetails;
import com.aleonets.task.task1.service.PropertyDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/details")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PropertyDetailsController {

	PropertyDetailsService propertyDetailsService;

	@GetMapping("/{propertyId}")
	public PropertyDetails find(@PathVariable String propertyId, @RequestParam String user) {
		return propertyDetailsService.getPropertyDetails(propertyId, user);
	}
}
