package com.aleonets.task.task1.service;

import static java.lang.Math.sqrt;

import com.aleonets.task.task1.api.model.Property;
import com.aleonets.task.task1.api.model.PropertyDetails;
import com.aleonets.task.task1.storage.entity.PropertyEntity;
import com.aleonets.task.task1.storage.entity.UserPropertyEntity;
import com.aleonets.task.task1.storage.mapping.PropertyDetailsMapping;
import com.aleonets.task.task1.storage.mapping.PropertyMapping;
import com.aleonets.task.task1.storage.repository.PropertyRepository;
import com.aleonets.task.task1.storage.repository.UserPropertyRepository;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PropertyDetailsService {

	PropertyRepository propertyRepository;
	UserPropertyRepository userPropertyRepository;
	PropertyDetailsMapping propertyDetailsMapping;
	PropertyMapping propertyMapping;

	@Transactional
	public PropertyDetails getPropertyDetails(String propertyId, String user) {
		var mainPropertyEntity = propertyRepository.findById(propertyId).orElseThrow();
		PropertyDetails propertyDetails = propertyDetailsMapping.toModel(mainPropertyEntity);
		propertyDetails.setSimilarProperty(getListOfSimilarProperties(mainPropertyEntity));

		addUserProperty(propertyId, user);

		return propertyDetails;
	}

	private void addUserProperty(String propertyId, String user) {
		userPropertyRepository.save(UserPropertyEntity.builder().id(UUID.randomUUID()).user(user).property(propertyId).build());
	}

	private List<Property> getListOfSimilarProperties(PropertyEntity mainProperty) {
		var listOfEntities = getSimilarProperties(mainProperty).stream().map(PropertyWithSimilarity::getPropertyEntity).toList();
		return propertyMapping.toModel(listOfEntities);
	}

	private SortedSet<PropertyWithSimilarity> getSimilarProperties(PropertyEntity mainProperty) {
		var candidates = propertyRepository.findAllByIdIsNot(mainProperty.getId());
		SortedSet<PropertyWithSimilarity> resultSet = new TreeSet<>(Collections.reverseOrder());
		candidates.forEach(candidate -> {
			var similarity = calculateSimilarity(mainProperty, candidate);
			if (similarity > 0) {
				resultSet.add(
						PropertyWithSimilarity.builder()
								.propertyEntity(candidate)
								.similarity(similarity)
								.build()

				);
			}
		});
		return resultSet;
	}

	private Double calculateSimilarity(PropertyEntity first, PropertyEntity second) {
		List<String> allUsers = userPropertyRepository.findAllUsers();
		List<UserPropertyEntity> allUserProperties = userPropertyRepository.findAll();
		log.info("{} vs {}", first.getId(), second.getId());
		var res = getTop(allUsers, allUserProperties, first, second) / (getDiv(allUsers, allUserProperties, first) * getDiv(allUsers, allUserProperties,
				second));
		log.info("sim: {}", res);
		return res;
	}

	private Double getTop(List<String> allUsers, List<UserPropertyEntity> allUserProperties, PropertyEntity first, PropertyEntity second) {
		double result = 0D;
		StringBuilder text = new StringBuilder("(");
		for (String user : allUsers) {
			var firstCount = countProperties(user, first.getId(), allUserProperties);
			var secondCount = countProperties(user, second.getId(), allUserProperties);
			text.append(firstCount).append("*").append(secondCount).append("+");
			result += firstCount * secondCount;
		}
		log.info("TOP : {} = {}", text.substring(0, text.length() - 1) + ")", result);
		return result;
	}

	private Double getDiv(List<String> allUsers, List<UserPropertyEntity> allUserProperties, PropertyEntity property) {
		double result = 0D;
		StringBuilder text = new StringBuilder("(");
		for (String user : allUsers) {
			var count = countProperties(user, property.getId(), allUserProperties);
			text.append(count).append("^2+");
			result += Math.pow(count, 2);
		}
		log.info("BOT : {} = {}", text.substring(0, text.length() - 1) + ")", sqrt(result));
		return sqrt(result);
	}

	private int countProperties(String user, String propertyId, List<UserPropertyEntity> allUserProperties) {
		return allUserProperties.stream()
				.filter(userPropertyEntity -> userPropertyEntity.getUser().equals(user) && userPropertyEntity.getProperty().equals(propertyId)).toList()
				.size();
	}

	@Builder
	@Getter
	private static class PropertyWithSimilarity implements Comparable<PropertyWithSimilarity> {

		Double similarity;
		PropertyEntity propertyEntity;

		@Override
		public int compareTo(PropertyWithSimilarity another) {
			return similarity.compareTo(another.getSimilarity());
		}
	}

}
