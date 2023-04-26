import { Component, ViewEncapsulation } from '@angular/core';
import { ApiDocumentationApi } from './api-documentation-api.service';

@Component({
	selector: 'app-api-documentation',
	templateUrl: './api-documentation.component.html',
	styleUrls: ['./api-documentation.component.sass'],
	encapsulation: ViewEncapsulation.None
})
export class ApiDocumentationComponent {

	private apiDocumentationApi: ApiDocumentationApi;

	constructor(apiDocumentationApi: ApiDocumentationApi) {
		this.apiDocumentationApi = apiDocumentationApi;
	}

	getOpenApiSpecFullUrl() {
		return location.origin + "/api/v1/rest/common/download/stapi.yaml";
	}

	getOpenApiSpecFullUrlForEditor() {
		var prefix = location.origin;
		if (prefix === 'http://stapi.co') {
			prefix = 'https://stapi.co'; // Swagger Editor won't load URL over HTTP due to mixed content constraints
		}
		return prefix + "/api/v1/rest/common/download/stapi.yaml";
	}

	isNotStapiOverHttps() {
		return location.origin != 'https://stapi.co';
	}

}
