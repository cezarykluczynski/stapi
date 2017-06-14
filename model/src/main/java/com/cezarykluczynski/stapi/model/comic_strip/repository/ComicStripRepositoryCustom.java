package com.cezarykluczynski.stapi.model.comic_strip.repository;

import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface ComicStripRepositoryCustom extends CriteriaMatcher<ComicStripRequestDTO, ComicStrip> {
}
