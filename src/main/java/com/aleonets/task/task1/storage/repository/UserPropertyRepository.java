package com.aleonets.task.task1.storage.repository;

import com.aleonets.task.task1.storage.entity.UserPropertyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPropertyRepository extends JpaRepository<UserPropertyEntity, String> {

	@Query(
			"""
        select distinct user from user_property
  """)
	List<String> findAllUsers();

}
