package com.aleonets.task.task1.storage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor(force = true)
@Accessors(chain = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "properties")
@Getter
@Builder
public class PropertyEntity {

	@Id
	String id;

	String name;

	String details;

  double price;

	String image;
}
