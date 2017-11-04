import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { RestApiService } from '../rest-api/rest-api.service';
import { WindowReferenceService } from '../window-reference/window-reference.service';

import { PanelComponent } from './panel.component';

class RestApiServiceMock {
	public getMe() {}
	public getOAuthAuthorizeUrl() {}
}

class WindowReferenceServiceMock {
	public getWindow() {}
}

describe('PanelComponent', () => {
	const NAME = 'NAME';
	const URL = 'URL';
	let component: PanelComponent;
	let fixture: ComponentFixture<PanelComponent>;
	let restApiServiceMock: RestApiServiceMock;
	let windowReferenceServiceMock: WindowReferenceServiceMock;

	beforeEach(async(() => {
		restApiServiceMock = new RestApiServiceMock();
		windowReferenceServiceMock = new WindowReferenceServiceMock()
		TestBed.configureTestingModule({
			declarations: [PanelComponent],
			providers: [
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				},
				{
					provide: WindowReferenceService,
					useValue: windowReferenceServiceMock
				}
			]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(PanelComponent);
		component = fixture.componentInstance;
	});

	describe('initialization without error', () => {
		beforeEach(() => {
			spyOn(restApiServiceMock, 'getMe').and.returnValue(new Promise((resolve) => {
				resolve({
					name: NAME
				});
			}));
			fixture.detectChanges();
		});

		it('is performed', () => {
			expect(component).toBeTruthy();

			fixture.detectChanges();

			fixture.whenStable().then(() => {
				expect(component.getName()).toEqual(NAME);
			});
		});
	});

	describe('initialization with error', () => {
		let window = {
			location: {
				href: ''
			}
		};

		beforeEach(() => {
			spyOn(restApiServiceMock, 'getMe').and.returnValue(new Promise((resolve, reject) => {
				reject({
					status: 403
				});
			}));
			spyOn(restApiServiceMock, 'getOAuthAuthorizeUrl').and.returnValue(new Promise((resolve) => {
				resolve({
					url: URL
				});
			}));
			spyOn(windowReferenceServiceMock, 'getWindow').and.returnValue(window);

			fixture.detectChanges();
		});

		it('is performed', () => {
			expect(component).toBeTruthy();
			expect(component.isAuthenticated()).toBeFalse();
			expect(component.isAuthenticationRequired()).toBeFalse();
			expect(component.isRedirecting()).toBeFalse();
			expect(component.getButtonLabel()).toBe('Authenticate with GitHub');
			fixture.detectChanges();

			fixture.whenStable().then(() => {
				expect(component.isAuthenticated()).toBeFalse();
				expect(component.isAuthenticationRequired()).toBeTrue();
				expect(component.isRedirecting()).toBeFalse();
				expect(component.getButtonLabel()).toBe('Authenticate with GitHub');
				fixture.detectChanges();

				fixture.debugElement.query(By.css('.panel-github-authenticate')).nativeElement.click();

				fixture.whenStable().then(() => {
					expect(component.getName()).toBeUndefined();
					expect(window.location.href).toBe(URL);
					expect(component.isAuthenticated()).toBeFalse();
					expect(component.isAuthenticationRequired()).toBeTrue();
					expect(component.isRedirecting()).toBeTrue();
					expect(component.getButtonLabel()).toBe('Redirecting to GitHub...');
				});
			});
		});
	});
});
