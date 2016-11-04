package com.cezarykluczynski.stapi.model.staff.repository;

import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long>, StaffRepositoryCustom {
}
