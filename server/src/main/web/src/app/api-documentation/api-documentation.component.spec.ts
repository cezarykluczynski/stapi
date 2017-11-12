import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import * as hljs from 'highlight.js';
import { HighlightJsModule, HIGHLIGHT_JS } from 'angular-highlight-js';

import { RestApiService } from '../rest-api/rest-api.service';
import { ApiDocumentationComponent } from './api-documentation.component';
import { ApiDocumentationApi } from './api-documentation-api.service';

export function highlightJsFactory() {
	return hljs;
}

const REST_DOCUMENT_CONTENT = 'REST_DOCUMENT_CONTENT';
const REST_DOCUMENT_2_CONTENT = 'REST_DOCUMENT_2_CONTENT';
const SOAP_DOCUMENT_CONTENT = 'SOAP_DOCUMENT_CONTENT';

const REST_DOCUMENT = {
	content: REST_DOCUMENT_CONTENT
}
const REST_DOCUMENT_2 = {
	content: REST_DOCUMENT_2_CONTENT
};
const SOAP_DOCUMENT = {
	content: SOAP_DOCUMENT_CONTENT
};

class ApiDocumentationApiMock {
	public getDocumentation() {
		return {
			restDocuments: [REST_DOCUMENT, REST_DOCUMENT_2],
			soapDocuments: [SOAP_DOCUMENT],
		};
	}
}

describe('ApiDocumentationComponent', () => {
	let component: ApiDocumentationComponent;
	let fixture: ComponentFixture<ApiDocumentationComponent>;
	let apiDocumentationApiMock: ApiDocumentationApiMock;

	beforeEach(async(() => {
		apiDocumentationApiMock = new ApiDocumentationApiMock();

		TestBed.configureTestingModule({
			declarations: [ApiDocumentationComponent],
			imports: [
				HighlightJsModule.forRoot({
					provide: HIGHLIGHT_JS,
					useFactory: highlightJsFactory
				})
			],
			providers: [
				{
					provide: ApiDocumentationApi,
					useValue: apiDocumentationApiMock
				}
			]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ApiDocumentationComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('creates', () => {
		expect(component).toBeTruthy();
	});

	it('gets REST specs zip link', () => {
		expect(component.getRestSpecsZipLink()).toContain('common/download/zip/rest');
	});

	it('gets SOAP contracts zip link', () => {
		expect(component.getSoapContractsZipLink()).toContain('common/download/zip/soap');
	});

	it('switch between documentation types', () => {
		expect(component.isRestDocumentation()).toBeTrue();
		expect(component.isSoapDocumentation()).toBeFalse();
		expect(component.getFilesListClass()).toBe('col-md-5');
		expect(component.getFileContentClass()).toBe('col-md-7');
		expect(component.getLinks()).toEqual([REST_DOCUMENT, REST_DOCUMENT_2]);
		expect(component.getSelectedFileContents()).toBe(REST_DOCUMENT_CONTENT);
		expect(component.getFileType()).toBe('yaml');

		fixture.debugElement.nativeElement.querySelector('.soap-select').click();

		expect(component.isRestDocumentation()).toBeFalse();
		expect(component.isSoapDocumentation()).toBeTrue();
		expect(component.getFilesListClass()).toBe('col-md-3');
		expect(component.getFileContentClass()).toBe('col-md-9');
		expect(component.getLinks()).toEqual([SOAP_DOCUMENT]);
		expect(component.getSelectedFileContents()).toBe(SOAP_DOCUMENT_CONTENT);
		expect(component.getFileType()).toBe('xml');

		fixture.debugElement.nativeElement.querySelector('.rest-select').click();

		expect(component.isRestDocumentation()).toBeTrue();
		expect(component.isSoapDocumentation()).toBeFalse();
		expect(component.getFilesListClass()).toBe('col-md-5');
		expect(component.getFileContentClass()).toBe('col-md-7');
		expect(component.getLinks()).toEqual([REST_DOCUMENT, REST_DOCUMENT_2]);
		expect(component.getSelectedFileContents()).toBe(REST_DOCUMENT_CONTENT);
		expect(component.getFileType()).toBe('yaml');
	});

	it('selects file', () => {
		component.selectFile(1);

		fixture.whenStable().then(() => {
			expect(component.getSelectedFileContents()).toEqual(REST_DOCUMENT_2_CONTENT);
		});
	});

	it('stops propagation', () => {
		expect(() => {
			component.stopPropagation(undefined);
		}).not.toThrow();

		const event = {
			stopPropagation: jasmine.createSpy('stopPropagation')
		};
		component.stopPropagation(event);

		expect(event.stopPropagation).toHaveBeenCalled();
	});
});
