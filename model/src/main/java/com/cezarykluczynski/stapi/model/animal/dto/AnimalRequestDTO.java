package com.cezarykluczynski.stapi.model.animal.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AnimalRequestDTO {

	private String uid;

	private String name;

	private Boolean earthAnimal;

	private Boolean earthInsect;

	private Boolean avian;

	private Boolean canine;

	private Boolean feline;

	private RequestSortDTO sort;

}
