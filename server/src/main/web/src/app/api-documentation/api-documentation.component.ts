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

	getSwaggerSpecFullUrl() {
		return location.origin + "/api/v1/rest/common/download/stapi.yaml";
	}

	isNotStapiOverHttps() {
		return location.origin != 'https://stapi.co';
	}

}
