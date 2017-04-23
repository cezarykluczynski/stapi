package com.cezarykluczynski.stapi.sources.mediawiki.parser

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class JsonTemplateParserTest extends Specification {

	private static final String XML = """
		<root>
			<template>
				<title>realworld</title>
			</template>
			<template lineStart="1">
				<title>${TemplateTitle.SIDEBAR_ACTOR} </title>
				<part><name>Name </name>=<value> Sir Patrick Stewart </value></part>
				<part><name>Birth name </name>=<value> Patrick Hewes Stewart </value></part>
				<part>
					<name>Date of birth </name>=
					<value>
						content
						<template>
							<title>d</title>
							<part><name index="1"/><value>13</value></part>
							<part><name index="2"/><value>July</value></part>
							<part><name index="3"/><value>1940</value></part>
						</template>
					</value>
				</part>
			</template>
			<template>
				<title>born</title>
				<part><name index="1"/><value>13</value></part>
				<part><name index="2"/><value>July</value></part>
				<part><name index="3"/><value>1940</value></part>
			</template>
		</root>
	"""

	private static final String XML_WITHOUT_TEMPLATE = '<root></root>'

	void "converts XML to Template"() {
		when:
		List<Template> template = new JsonTemplateParser().parse(XML)

		then:
		template[0] == new Template(title: 'realworld')
		template[1].title == TemplateTitle.SIDEBAR_ACTOR
		template[1].parts[0] == new Template.Part(key: 'name', value: 'Sir Patrick Stewart')
		template[1].parts[1] == new Template.Part(key: 'birth name', value: 'Patrick Hewes Stewart')
		template[1].parts[2].key == 'date of birth'
		template[1].parts[2].value == null
		template[1].parts[2].content == 'content'
		template[1].parts[2].templates[0].title == TemplateTitle.D
		template[1].parts[2].templates[0].parts[0] == new Template.Part(key: '1', value: '13')
		template[1].parts[2].templates[0].parts[1] == new Template.Part(key: '2', value: 'July')
		template[1].parts[2].templates[0].parts[2] == new Template.Part(key: '3', value: '1940')
		template[2].title == 'born'
		template[2].parts[0] == new Template.Part(key: '1', value: '13')
		template[2].parts[1] == new Template.Part(key: '2', value: 'July')
		template[2].parts[2] == new Template.Part(key: '3', value: '1940')
	}

	void "converts XML without templates"() {
		when:
		List<Template> templates = new JsonTemplateParser().parse(XML_WITHOUT_TEMPLATE)

		then:
		templates.empty
	}

}
