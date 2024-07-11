package com.aleonets.task.task1.storage.repository;

import com.aleonets.task.task1.storage.entity.PropertyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, String> {

	List<PropertyEntity> findAllByIdIsNot(String exceptId);
}
