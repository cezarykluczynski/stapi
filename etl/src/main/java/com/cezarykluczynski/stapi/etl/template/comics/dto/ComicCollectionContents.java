package com.cezarykluczynski.stapi.etl.template.comics.dto;

import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class ComicCollectionContents {

	private Set<Comics> comics = Sets.newHashSet();
	private Set<ComicSeries> comicSeries = Sets.newHashSet();

}
