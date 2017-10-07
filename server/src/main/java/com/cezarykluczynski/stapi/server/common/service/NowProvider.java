package com.cezarykluczynski.stapi.server.common.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NowProvider {

	public LocalDateTime now() {
		return LocalDateTime.now();
	}

}
