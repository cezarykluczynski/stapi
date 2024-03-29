package com.cezarykluczynski.stapi.model.food.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = FoodRepository.class, singularName = "food", pluralName = "foods")
public class Food extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_sequence_generator")
	@SequenceGenerator(name = "food_sequence_generator", sequenceName = "food_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean earthlyOrigin;

	private Boolean dessert;

	private Boolean fruit;

	private Boolean herbOrSpice;

	private Boolean sauce;

	private Boolean soup;

	private Boolean beverage;

	private Boolean alcoholicBeverage;

	private Boolean juice;

	private Boolean tea;

}
