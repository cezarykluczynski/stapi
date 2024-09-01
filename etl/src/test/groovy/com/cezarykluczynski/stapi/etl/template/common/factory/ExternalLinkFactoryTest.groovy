package com.cezarykluczynski.stapi.etl.template.common.factory

import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkType
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkTypeVariant
import spock.lang.Specification

class ExternalLinkFactoryTest extends Specification {

	private static final String RESOURCE_ID = 'RESOURCE_ID'
	private static final String UID = 'UID'

	private UidGenerator uidGeneratorMock
	private ExternalLinkFactory externalLinkFactory

	void setup() {
		uidGeneratorMock = Mock()
		externalLinkFactory = new ExternalLinkFactory(uidGeneratorMock)
	}

	void "creates link with type and no type variant"() {
		when:
		ExternalLink externalLink = externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.FACEBOOK, null, RESOURCE_ID)

		then:
		1 * uidGeneratorMock.generateForExternalLink("https://www.facebook.com/$RESOURCE_ID") >> UID
		0 * _
		externalLink.type == ExternalLinkType.FACEBOOK
		externalLink.variant == null
		externalLink.resourceId == RESOURCE_ID
		externalLink.link == "https://www.facebook.com/$RESOURCE_ID"
		externalLink.uid == UID
	}

	void "creates external link without type, but with type variant"() {
		when:
		ExternalLink externalLink = externalLinkFactory
				.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.YOUTUBE_USER, RESOURCE_ID)

		then:
		1 * uidGeneratorMock.generateForExternalLink("https://www.youtube.com/user/$RESOURCE_ID") >> UID
		0 * _
		externalLink.type == ExternalLinkType.YOUTUBE
		externalLink.variant == ExternalLinkTypeVariant.YOUTUBE_USER
		externalLink.resourceId == RESOURCE_ID
		externalLink.link == "https://www.youtube.com/user/$RESOURCE_ID"
		externalLink.uid == UID
	}

	void "throws exception when there is no type nor type variant"() {
		when:
		externalLinkFactory.ofTypeVariantAndResourceId(null, null, RESOURCE_ID)

		then:
		0 * _
		thrown(RuntimeException)
	}

}
