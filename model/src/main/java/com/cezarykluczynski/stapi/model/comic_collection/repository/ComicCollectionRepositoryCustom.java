package com.cezarykluczynski.stapi.model.comic_collection.repository;

import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface ComicCollectionRepositoryCustom extends CriteriaMatcher<ComicCollectionRequestDTO, ComicCollection> {
}
