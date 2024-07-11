package com.aleonets.task.task1.storage.mapping;

import com.aleonets.task.task1.api.model.Property;
import com.aleonets.task.task1.api.model.PropertyDetails;
import com.aleonets.task.task1.storage.entity.PropertyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PropertyDetailsMapping {

	@Mapping(target = "similarProperty", ignore = true)
	PropertyDetails toModel(PropertyEntity entity);
}
