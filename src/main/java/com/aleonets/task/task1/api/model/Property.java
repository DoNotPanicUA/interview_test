package com.aleonets.task.task1.api.model;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true)
public class Property {

	String id;

	String name;

	double price;
}
