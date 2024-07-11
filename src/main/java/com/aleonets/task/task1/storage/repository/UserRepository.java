package com.aleonets.task.task1.storage.repository;

import com.aleonets.task.task1.storage.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	UserEntity getByEmailAndAndIsActiveIsTrue(String email);
}
