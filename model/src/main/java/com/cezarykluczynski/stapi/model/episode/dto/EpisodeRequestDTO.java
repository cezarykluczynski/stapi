package com.cezarykluczynski.stapi.model.episode.dto;


import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class EpisodeRequestDTO {

	private String uid;

	private String title;

	private Integer seasonNumberFrom;

	private Integer seasonNumberTo;

	private Integer episodeNumberFrom;

	private Integer episodeNumberTo;

	private String productionSerialNumber;

	private Boolean featureLength;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private LocalDate usAirDateFrom;

	private LocalDate usAirDateTo;

	private LocalDate finalScriptDateFrom;

	private LocalDate finalScriptDateTo;

	private RequestSortDTO sort;

}
