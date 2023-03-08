package com.cezarykluczynski.stapi.server.common.reader;

public interface FullReader<O> {

	O readFull(String uid);

}
