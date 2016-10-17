package com.cezarykluczynski.stapi.model.page.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Page {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="page_sequence_generator")
	@SequenceGenerator(name="page_sequence_generator", sequenceName="page_sequence", allocationSize = 1)
	private Long id;

	private Long pageId;

	private String title;

}
