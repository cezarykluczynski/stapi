package com.cezarykluczynski.stapi.etl.staff.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffWriter implements ItemWriter<Staff> {

	private StaffRepository staffRepository;

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	@Inject
	public StaffWriter(StaffRepository staffRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.staffRepository = staffRepository;
		this.duplicateFilteringPreSavePageAwareProcessor  = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Staff> items) throws Exception {
		staffRepository.save(process(items));
	}

	private List<Staff> process(List<? extends Staff> staffList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(staffList.stream()
				.map(staff -> (PageAware) staff)
				.collect(Collectors.toList()), Staff.class).stream()
				.map(pageAware -> (Staff) pageAware)
				.collect(Collectors.toList());
	}

}
