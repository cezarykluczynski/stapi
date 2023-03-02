package com.cezarykluczynski.stapi.model.staff.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.movie.entity.Movie_;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.entity.Staff_;
import com.cezarykluczynski.stapi.model.staff.query.StaffInitialQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StaffRepositoryImpl extends AbstractRepositoryImpl<Staff> implements StaffRepositoryCustom {

	private final StaffInitialQueryBuilderFactory staffInitialQueryBuilderFactory;

	public StaffRepositoryImpl(StaffInitialQueryBuilderFactory staffInitialQueryBuilderFactory) {
		this.staffInitialQueryBuilderFactory = staffInitialQueryBuilderFactory;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Staff> findMatching(StaffRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Staff> staffQueryBuilder = createInitialStaffQueryBuilder(criteria, pageable);
		boolean doFetch = criteria.getUid() != null;

		Page<Staff> staffPage;

		if (doFetch) {
			staffQueryBuilder.fetch(Staff_.writtenEpisodes);
			staffQueryBuilder.fetch(Staff_.teleplayAuthoredEpisodes);
			staffQueryBuilder.fetch(Staff_.storyAuthoredEpisodes);
			staffQueryBuilder.fetch(Staff_.directedEpisodes);
			staffQueryBuilder.fetch(Staff_.episodes);
			staffPage = staffQueryBuilder.findPage();

			List<Staff> staffList = staffPage.getContent();

			if (staffList.size() == 0) {
				return staffPage;
			}

			Staff staff = staffList.get(0);

			QueryBuilder<Staff> staffMoviesQueryBuilder = createInitialStaffQueryBuilder(criteria, pageable);

			staffMoviesQueryBuilder.fetch(Staff_.writtenMovies);
			staffMoviesQueryBuilder.fetch(Staff_.screenplayAuthoredMovies);
			staffMoviesQueryBuilder.fetch(Staff_.storyAuthoredMovies);
			staffMoviesQueryBuilder.fetch(Staff_.directedMovies);
			staffMoviesQueryBuilder.fetch(Staff_.producedMovies);
			staffMoviesQueryBuilder.fetch(Staff_.movies);
			staffMoviesQueryBuilder.fetch(Staff_.movies, Movie_.mainDirector);

			List<Staff> moviesStaffList = staffMoviesQueryBuilder.findAll();

			if (moviesStaffList.size() == 1) {
				Staff moviesStaff = moviesStaffList.get(0);
				staff.setWrittenMovies(moviesStaff.getWrittenMovies());
				staff.setScreenplayAuthoredMovies(moviesStaff.getScreenplayAuthoredMovies());
				staff.setStoryAuthoredMovies(moviesStaff.getStoryAuthoredMovies());
				staff.setDirectedMovies(moviesStaff.getWrittenMovies());
				staff.setProducedMovies(moviesStaff.getProducedMovies());
				staff.setMovies(moviesStaff.getMovies());
			}
		} else {
			staffPage = staffQueryBuilder.findPage();
		}

		clearProxies(staffPage, !doFetch);
		return staffPage;
	}

	@Override
	protected void clearProxies(Page<Staff> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(staff -> {
			staff.setWrittenEpisodes(Sets.newHashSet());
			staff.setTeleplayAuthoredEpisodes(Sets.newHashSet());
			staff.setStoryAuthoredEpisodes(Sets.newHashSet());
			staff.setDirectedEpisodes(Sets.newHashSet());
			staff.setEpisodes(Sets.newHashSet());
			staff.setWrittenMovies(Sets.newHashSet());
			staff.setScreenplayAuthoredMovies(Sets.newHashSet());
			staff.setWrittenEpisodes(Sets.newHashSet());
			staff.setStoryAuthoredMovies(Sets.newHashSet());
			staff.setDirectedMovies(Sets.newHashSet());
			staff.setProducedMovies(Sets.newHashSet());
			staff.setMovies(Sets.newHashSet());
		});
	}

	private QueryBuilder<Staff> createInitialStaffQueryBuilder(StaffRequestDTO criteria, Pageable pageable) {
		return staffInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

}
