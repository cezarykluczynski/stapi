package com.cezarykluczynski.stapi.model.soundtrack.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundtrackRepository extends JpaRepository<Soundtrack, Long>, PageAwareRepository<Soundtrack>, SoundtrackRepositoryCustom {
}
