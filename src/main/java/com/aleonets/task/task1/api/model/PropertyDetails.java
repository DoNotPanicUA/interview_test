package com.aleonets.task.task1.api.model;

import java.util.List;
import lombok.Builder;
import lombok.Setter;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true)
public class PropertyDetails {

	String id;

	String name;

	double price;

	String description;

	@Setter
	List<Property> similarProperty;
}
