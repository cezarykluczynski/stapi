import { TestBed, async } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';
import { ApiDocumentationApi } from './api-documentation/api-documentation-api.service';
import {RouterTestingModule} from '@angular/router/testing';

class FeatureSwitchApiMock {
	public isEnabled() {}
}

class ApiDocumentationApiMock {
	public getDataVersion() {}
	public getGitHubStargazersCount() {
		return 101;
	}
}

describe('AppComponent', () => {
	let featureSwitchApiMock: FeatureSwitchApiMock;
	let apiDocumentationApiMock: ApiDocumentationApiMock;

	beforeEach(async(() => {
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

	it('creates the app', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.debugElement.componentInstance;
		expect(app).toBeTruthy();
	}));

	it('load stargazers count from API', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		expect(fixture.componentInstance.hasGitHubStargazersCount()).toBeFalse();

		fixture.componentInstance.ngOnInit();
		fixture.detectChanges();

		fixture.whenStable().then(() => {
			expect(fixture.componentInstance.hasGitHubStargazersCount()).toBeTrue();
			expect(fixture.componentInstance.getGitHubStarsCount()).toBe(101);
		});
	}));
});
