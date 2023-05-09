package com.cezarykluczynski.stapi.etl.staff.creation.processor;

import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class StaffWriter implements ItemWriter<Staff> {

	private final StaffRepository staffRepository;

	public StaffWriter(StaffRepository staffRepository) {
		this.staffRepository = staffRepository;
	}

	@Override
	public void write(Chunk<? extends Staff> items) throws Exception {
		staffRepository.saveAll(items.getItems());
	}

}
