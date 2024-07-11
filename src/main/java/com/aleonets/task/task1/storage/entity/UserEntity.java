package com.aleonets.task.task1.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Entity(name = "users")
@Getter
@Builder
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uuid;

	@NotBlank
	@Email
	String email;

	@NotBlank
	String password;

	@Column(name = "is_active")
	boolean isActive;
}
