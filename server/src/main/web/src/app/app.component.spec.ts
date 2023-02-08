import {TestBed, waitForAsync} from '@angular/core/testing';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {AppComponent} from './app.component';
import {FeatureSwitchApi} from './feature-switch/feature-switch-api.service';
import {ApiDocumentationApi} from './api-documentation/api-documentation-api.service';
import {RouterTestingModule} from '@angular/router/testing';

class FeatureSwitchApiMock {
	public isEnabled() {
	}
}

class ApiDocumentationApiMock {
	public getDataVersion() {
	}
}

describe('AppComponent', () => {
	let featureSwitchApiMock: FeatureSwitchApiMock;
	let apiDocumentationApiMock: ApiDocumentationApiMock;

	beforeEach(waitForAsync(() => {
		featureSwitchApiMock = new FeatureSwitchApiMock();
		apiDocumentationApiMock = new ApiDocumentationApiMock();

		TestBed.configureTestingModule({
			imports: [RouterTestingModule.withRoutes([])],
			schemas: [NO_ERRORS_SCHEMA],
			declarations: [AppComponent],
			providers: [
				{
					provide: FeatureSwitchApi,
					useValue: featureSwitchApiMock
				},
				{
					provide: ApiDocumentationApi,
					useValue: apiDocumentationApiMock
				}
			]
		}).compileComponents();
	}));

	it('creates the app', waitForAsync(() => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.debugElement.componentInstance;
		expect(app).toBeTruthy();
	}));
});
