package com.cezarykluczynski.stapi.model.comicCollection.repository;

import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface ComicCollectionRepositoryCustom extends CriteriaMatcher<ComicCollectionRequestDTO, ComicCollection> {
}
