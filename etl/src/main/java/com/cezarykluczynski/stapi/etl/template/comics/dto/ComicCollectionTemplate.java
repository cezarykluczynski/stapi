package com.cezarykluczynski.stapi.etl.template.comics.dto;

import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class ComicCollectionTemplate extends ComicsTemplate {

	private Set<Comics> comics = Sets.newHashSet();

}
