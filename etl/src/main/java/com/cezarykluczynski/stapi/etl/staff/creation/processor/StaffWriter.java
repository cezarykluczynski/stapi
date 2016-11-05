package com.cezarykluczynski.stapi.etl.staff.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.page.service.DuplicateReattachingPreSavePageAwareFilter;
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

	private DuplicateReattachingPreSavePageAwareFilter duplicateReattachingPreSavePageAwareFilter;

	@Inject
	public StaffWriter(StaffRepository staffRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor,
			DuplicateReattachingPreSavePageAwareFilter duplicateReattachingPreSavePageAwareFilter) {
		this.staffRepository = staffRepository;
		this.duplicateFilteringPreSavePageAwareProcessor  = duplicateFilteringPreSavePageAwareProcessor;
		this.duplicateReattachingPreSavePageAwareFilter = duplicateReattachingPreSavePageAwareFilter;
	}

	@Override
	public void write(List<? extends Staff> items) throws Exception {
		List<Staff> staffListWithoutDuplicates = filterDuplicates(items);
		List<Staff> staffListWithAttachedPages = reattach(staffListWithoutDuplicates);
		List<Staff> staffListWithAttachedPagesAndWithoutDuplicates = filterDuplicates(staffListWithAttachedPages);
		staffRepository.save(staffListWithAttachedPagesAndWithoutDuplicates);
	}

	private List<Staff> filterDuplicates(List<? extends Staff> staffList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(staffList.stream()
				.map(staff -> (PageAware) staff)
				.collect(Collectors.toList()), Staff.class).stream()
				.map(pageAware -> (Staff) pageAware)
				.collect(Collectors.toList());
	}

	private List<Staff> reattach(List<Staff> staffList) {
		return duplicateReattachingPreSavePageAwareFilter.process(staffList.stream()
				.map(staff -> (PageAware) staff)
				.collect(Collectors.toList()), Staff.class).stream()
				.map(pageAware -> (Staff) pageAware)
				.collect(Collectors.toList());
	}

}
