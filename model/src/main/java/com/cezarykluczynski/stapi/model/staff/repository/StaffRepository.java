package com.cezarykluczynski.stapi.model.staff.repository;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long>, StaffRepositoryCustom {

	Optional<Staff> findByPageTitleAndPageMediaWikiSource(String name, MediaWikiSource mediaWikiSource);

	Optional<Staff> findByPagePageIdAndPageMediaWikiSource(Long pageId, MediaWikiSource mediaWikiSource);

}
