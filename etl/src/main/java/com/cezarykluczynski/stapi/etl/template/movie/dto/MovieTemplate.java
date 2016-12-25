package com.cezarykluczynski.stapi.etl.template.movie.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieTemplate extends ImageTemplate {

	private Page page;

	private Movie movieStub;

	private String title;

	private String titleBulgarian;

	private String titleCatalan;

	private String titleChineseTraditional;

	private String titleGerman;

	private String titleItalian;

	private String titleJapanese;

	private String titlePolish;

	private String titleRussian;

	private String titleSerbian;

	private String titleSpanish;

	private LocalDate usReleaseDate;

}
