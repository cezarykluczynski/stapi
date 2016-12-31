package com.cezarykluczynski.stapi.model.staff.repository;

import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long>, StaffRepositoryCustom {

	Optional<Staff> findByPageTitle(String name);

	Optional<Staff> findByPagePageId(Long pageId);

}
