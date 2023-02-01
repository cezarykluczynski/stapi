import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ApiDocumentationApi } from './api-documentation-api.service';
import * as $ from 'jquery';

@Component({
	selector: 'app-api-documentation',
	templateUrl: './api-documentation.component.html',
	styleUrls: ['./api-documentation.component.sass'],
	encapsulation: ViewEncapsulation.None
})
export class ApiDocumentationComponent implements OnInit {

	private selectedFilesIndex: any = 0;
	private selectedDocumentationType: string;
	private documentation: any;

	private apiDocumentationApi: ApiDocumentationApi;

	constructor(apiDocumentationApi: ApiDocumentationApi) {
		this.apiDocumentationApi = apiDocumentationApi;
		this.selectedDocumentationType = '';
	}

	ngOnInit() {
		this.selectedDocumentationType = 'REST';
		this.documentation = this.apiDocumentationApi.getDocumentation();
	}

	getRestSpecsZipLink() {
		return this.getLinkPrefix() + '/api/v1/rest/common/download/zip/rest';
	}

	isRestDocumentation() {
		return true;
	}

	selectRest() {
		this.selectedDocumentationType = 'REST';
		this.selectedFilesIndex = 0;
	}

	getLinks() {
		return this.documentation.restDocuments;
	}

	stopPropagation(event: any) {
		if (event && event.stopPropagation) {
			event.stopPropagation();
		}
	}

	selectFile(index: any) {
		/* eslint  @typescript-eslint/no-non-null-assertion:0 */
		const codePreviewScrollTop = $('#api-documentation__code-preview')!.offset()!.top;
		const navbarHeight = $('.navbar').outerHeight()!;
		if (window.scrollY > codePreviewScrollTop - navbarHeight) {
			window.scrollTo(window.scrollX, codePreviewScrollTop - navbarHeight);
		}
		this.selectedFilesIndex = index;
	}

	getSelectedFileContents() {
		return this.documentation.restDocuments[this.selectedFilesIndex].content;
	}

	getFileType() {
		return 'yaml';
	}

	private getLinkPrefix() {
		return location.href.includes('localhost:4200') ? 'http://localhost:8686' : '';
	}

}
