package com.cezarykluczynski.stapi.sources.mediawiki.parser

import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
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
		template == Lists.newArrayList(
				new Template(title: 'realworld'),
				new Template(title: TemplateTitle.SIDEBAR_ACTOR, parts: Lists.newArrayList(
						new Template.Part(key: 'name', value: 'Sir Patrick Stewart'),
						new Template.Part(key: 'birth name', value: 'Patrick Hewes Stewart'),
						new Template.Part(key: 'date of birth', value: null, templates: Lists.newArrayList(
								new Template(title: TemplateTitle.D, parts: Lists.newArrayList(
										new Template.Part(key: '1', value: '13'),
										new Template.Part(key: '2', value: 'July'),
										new Template.Part(key: '3', value: '1940'),
								))
						))
				)),
				new Template(title: 'born', parts: Lists.newArrayList(
						new Template.Part(key: '1', value: '13'),
						new Template.Part(key: '2', value: 'July'),
						new Template.Part(key: '3', value: '1940'),
				))
		)
	}

	void "converts XML without templates"() {
		when:
		List<Template> templates = new JsonTemplateParser().parse(XML_WITHOUT_TEMPLATE)

		then:
		templates.empty
	}

}
