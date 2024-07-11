package com.aleonets.task.task1.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
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
@Entity(name = "user_property")
@Getter
@Builder
public class UserPropertyEntity {
	@Id
	UUID id;
	String property;
	@Column(name = "user_email")
	String user;
}
