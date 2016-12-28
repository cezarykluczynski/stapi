package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;

import java.util.List;
import java.util.Set;

public interface MovieRealPeopleLinkingWorker extends LinkingWorker<Set<List<String>>, Movie> {
}
