package com.cezarykluczynski.stapi.server.common.startup

import org.springframework.context.event.ContextRefreshedEvent
import spock.lang.Specification

class ApplicationStartupBannerDisplayingEventListenerTest extends Specification {

	private PrintStream out

	private ByteArrayOutputStream byteArrayOutputStream

	private ApplicationStartupBannerDisplayingEventListener applicationStartupBannerDisplayingEventListener

	void setup() {
		out = System.out
		byteArrayOutputStream = new ByteArrayOutputStream()
		PrintStream printStreamInterceptor = new PrintStream(byteArrayOutputStream)
		System.setOut(printStreamInterceptor)
		applicationStartupBannerDisplayingEventListener = new ApplicationStartupBannerDisplayingEventListener()
	}

	void cleanup() {
		System.setOut(out)
	}

	void "writers banner on application startup"() {
		given:
		ContextRefreshedEvent contextRefreshedEvent = Mock()

		when:
		applicationStartupBannerDisplayingEventListener.onApplicationEvent(contextRefreshedEvent)

		then:
		byteArrayOutputStream.toString().contains('Joshua Bell')
	}

}
