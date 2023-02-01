import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';
import {ApiDocumentationComponent} from './api-documentation.component';
import {ApiDocumentationApi} from './api-documentation-api.service';
import {HighlightModule} from 'ngx-highlightjs';

const REST_DOCUMENT_CONTENT = 'REST_DOCUMENT_CONTENT';
const REST_DOCUMENT_2_CONTENT = 'REST_DOCUMENT_2_CONTENT';

const REST_DOCUMENT = {
	content: REST_DOCUMENT_CONTENT
};
const REST_DOCUMENT_2 = {
	content: REST_DOCUMENT_2_CONTENT
};

class ApiDocumentationApiMock {
	public getDocumentation() {
		return {
			restDocuments: [REST_DOCUMENT, REST_DOCUMENT_2],
		};
	}
}

describe('ApiDocumentationComponent', () => {
	let component: ApiDocumentationComponent;
	let fixture: ComponentFixture<ApiDocumentationComponent>;
	let apiDocumentationApiMock: ApiDocumentationApiMock;

	beforeEach(waitForAsync(() => {
		apiDocumentationApiMock = new ApiDocumentationApiMock();

		TestBed.configureTestingModule({
			declarations: [ApiDocumentationComponent],
			imports: [
				HighlightModule
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

	it('selects file', waitForAsync(() => {
		component.selectFile(1);

		fixture.whenStable().then(() => {
			expect(component.getSelectedFileContents()).toEqual(REST_DOCUMENT_2_CONTENT);
		});
	}));

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
