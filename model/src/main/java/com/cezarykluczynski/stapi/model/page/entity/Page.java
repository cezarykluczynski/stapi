package com.cezarykluczynski.stapi.model.page.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = PageRepository.class, apiEntity = false, singularName = "page", pluralName = "pages")
public class Page {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "page_sequence_generator")
	@SequenceGenerator(name = "page_sequence_generator", sequenceName = "page_sequence", allocationSize = 1)
	private Long id;

	private Long pageId;

	@Enumerated(EnumType.STRING)
	private MediaWikiSource mediaWikiSource;

	private String title;

}
