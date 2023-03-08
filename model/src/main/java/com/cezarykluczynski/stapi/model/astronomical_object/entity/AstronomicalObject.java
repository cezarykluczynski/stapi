package com.cezarykluczynski.stapi.model.astronomical_object.entity;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"location", "astronomicalObjects"})
@EqualsAndHashCode(callSuper = true, exclude = {"location", "astronomicalObjects"})
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = AstronomicalObjectRepository.class, singularName = "astronomical object",
		pluralName = "astronomical objects", restApiVersion = "v2")
public class AstronomicalObject extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "astronomical_object_sequence_generator")
	@SequenceGenerator(name = "astronomical_object_sequence_generator", sequenceName = "astronomical_object_sequence", allocationSize = 1)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private AstronomicalObjectType astronomicalObjectType;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	private AstronomicalObject location;

	@ManyToMany(mappedBy = "location")
	private Set<AstronomicalObject> astronomicalObjects;

}
