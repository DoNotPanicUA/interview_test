package com.aleonets.task.task1.storage.mapping;

import com.aleonets.task.task1.api.model.Property;
import com.aleonets.task.task1.storage.entity.PropertyEntity;
import java.util.List;
import java.util.SortedSet;
import org.mapstruct.Mapper;

@Mapper
public interface PropertyMapping {

	Property toModel(PropertyEntity entity);

	List<Property> toModel(List<PropertyEntity> entity);

}
