package com.cezarykluczynski.stapi.etl.movie.creation.dto;

import com.google.common.collect.Sets;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MovieLinkedTitlesDTO {

	private Set<List<String>> writers = Sets.newHashSet();

	private Set<List<String>> screenplayAuthors = Sets.newHashSet();

	private Set<List<String>> storyAuthors = Sets.newHashSet();

	private Set<List<String>> directors = Sets.newHashSet();

	private Set<List<String>> producers = Sets.newHashSet();

	private Set<List<String>> staff = Sets.newHashSet();

	private Set<List<String>> performers = Sets.newHashSet();

	private Set<List<String>> stuntPerformers = Sets.newHashSet();

	private Set<List<String>> standInPerformers = Sets.newHashSet();

}
