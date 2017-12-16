package com.cezarykluczynski.stapi.server.common.startup;

import com.google.common.collect.Lists;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.cxf.helpers.IOUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@SuppressFBWarnings("DE_MIGHT_IGNORE")
public class ApplicationStartupBannerDisplayingEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Resource resource = new ClassPathResource("banner-startup-ready.txt");
		try {
			InputStream resourceInputStream = resource.getInputStream();
			String banner = IOUtils.toString(resourceInputStream);
			Lists.newArrayList(banner.split("\\r?\\n")).forEach(System.out::println);
			System.out.println("");
		} catch (Exception e) {
			// do nothing
		}
	}

}
