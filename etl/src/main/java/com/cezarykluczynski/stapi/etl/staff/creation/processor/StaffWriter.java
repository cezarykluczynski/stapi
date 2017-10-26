package com.cezarykluczynski.stapi.etl.staff.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.page.service.DuplicateReattachingPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffWriter implements ItemWriter<Staff> {

	private final StaffRepository staffRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	private final DuplicateReattachingPreSavePageAwareFilter duplicateReattachingPreSavePageAwareFilter;

	public StaffWriter(StaffRepository staffRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor,
			DuplicateReattachingPreSavePageAwareFilter duplicateReattachingPreSavePageAwareFilter) {
		this.staffRepository = staffRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
		this.duplicateReattachingPreSavePageAwareFilter = duplicateReattachingPreSavePageAwareFilter;
	}

	@Override
	public void write(List<? extends Staff> items) throws Exception {
		staffRepository.save(process(items));
	}

	private List<Staff> process(List<? extends Staff> performerList) {
		List<Staff> staffListWithoutExtends = fromExtendsListToStaffList(performerList);
		List<Staff> staffListWithoutDuplicates = filterDuplicates(staffListWithoutExtends);
		List<Staff> staffListWithAttachedPages = reattach(staffListWithoutDuplicates);
		return filterDuplicates(staffListWithAttachedPages);
	}

	private List<Staff> fromExtendsListToStaffList(List<? extends Staff> performerList) {
		return performerList
				.stream()
				.map(pageAware -> (Staff) pageAware)
				.collect(Collectors.toList());
	}

	private List<Staff> filterDuplicates(List<Staff> staffList) {
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
