package com.cezarykluczynski.stapi.model.soundtrack.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;

public interface SoundtrackRepositoryCustom extends CriteriaMatcher<SoundtrackRequestDTO, Soundtrack> {
}
