package com.cezarykluczynski.stapi.model.comicStrip.repository;

import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface ComicStripRepositoryCustom extends CriteriaMatcher<ComicStripRequestDTO, ComicStrip> {
}
