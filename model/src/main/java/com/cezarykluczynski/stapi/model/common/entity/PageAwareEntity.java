package com.cezarykluczynski.stapi.model.common.entity;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@MappedSuperclass
@Data
@EqualsAndHashCode(exclude = "page")
@ToString
public class PageAwareEntity {

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "page_id")
	private Page page;

	@Column(length = 14, name = "u_id")
	private String uid;

}
