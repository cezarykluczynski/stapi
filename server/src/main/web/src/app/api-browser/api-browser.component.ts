import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import { RestApiService } from '../rest-api/rest-api.service';
import { ApiBrowserApi } from './api-browser-api.service';

@Component({
	selector: 'app-api-browser',
	templateUrl: './api-browser.component.html',
	styleUrls: ['./api-browser.component.sass'],
	encapsulation: ViewEncapsulation.None
})
export class ApiBrowserComponent implements OnInit {

	private apiBrowserApi: ApiBrowserApi;
	private restApiService: RestApiService;
	private details: any;
	private options: Array<any>;
	private lastUpdateWasError = false;
	private response: any;
	public symbol: any;
	public phrase: any = '';
	public latestLookupPhrase: any = '';
	public loaded = false;

	constructor(apiBrowserApi: ApiBrowserApi, restApiService: RestApiService) {
		this.apiBrowserApi = apiBrowserApi;
		this.restApiService = restApiService;
    this.options = [];
	}

	ngOnInit() {
		this.details = this.apiBrowserApi.getDetails();
		this.options = this.details;
		this.symbol = this.options[0].symbol;
		this.loaded = true;
	}

	submit($event: any) {
		$event.preventDefault();
		this.latestLookupPhrase = this.phrase;
		this.handleRequest(this.apiBrowserApi.search(this.symbol, this.phrase, false));
	}

	handleRequest(promise: any) {
		promise.then((response: any) => {
			this.lastUpdateWasError = false;
			this.response = response;
		}).catch((error: any) => {
			this.lastUpdateWasError = true;
			this.response = JSON.parse(error.response);
		});
	}

	isDisabled() {
		return false;
	}

	getOptions() {
		return this.options;
	}

	hasMetadata() {
		return !this.lastUpdateWasError && this.hasContent();
	}

	hasError() {
		return this.lastUpdateWasError;
	}

	hasContent() {
		return !!(this.response && this.response.content);
	}

	getContent(): Array<any> {
		return this.response && this.response.content ? this.response.content : [];
	}

	getProperties(item: any) {
		const properties:  Array<any> = [];
		for (const i in item) {
			/* eslint no-prototype-builtins:0 */
			if (item.hasOwnProperty(i)) {
				properties.push({
					key: i,
					value: item[i]
				});
			}
		}
		return properties;
	}

	hasElements() {
		return !!(this.response && this.response.page && this.response.page.totalElements > 0);
	}

	getTotalResults() {
		return this.response && this.response.page && this.response.page.totalElements ? this.response.page.totalElements : 0;
	}

	hasMoreThanLimitResults() {
		return this.getTotalResults() > 50;
	}

	valueIsScalar(value: any) {
		return typeof value !== 'object';
	}

	isQualifiedItemProperty(property: any) {
		return !!(property.key !== 'uid' && property.value);
	}

	getInfo() {
		return 'This is API Browser. Existing API entities can be queried here by name or title. <br>' +
				'Pick resource to search in, type in full name or title, or part of name or title, then press enter or click "Submit".';
	}

}
