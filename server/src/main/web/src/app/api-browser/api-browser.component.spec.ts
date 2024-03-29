import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { By } from '@angular/platform-browser';

import { ApiBrowserComponent } from './api-browser.component';
import { ApiBrowserApi } from './api-browser-api.service';
import { RestApiService } from '../rest-api/rest-api.service';

class ApiBrowserApiMock {
	public getStatistics(): any {}
	public getDetails(): any {}
	public search(): any {}
}

class RestApiServiceMock {
}

describe('ApiBrowserComponent', () => {
	let component: ApiBrowserComponent;
	let fixture: ComponentFixture<ApiBrowserComponent>;
	let apiBrowserApiMock: ApiBrowserApiMock;
	let restApiServiceMock: RestApiServiceMock;
	const details: any[] = [
		{
			symbol: 'AN',
			name: 'Animal'
		},
		{
			symbol: 'AS',
			name: 'Astronomical object'
		}
	];

	beforeEach(waitForAsync(() => {
		restApiServiceMock = new RestApiServiceMock();
		apiBrowserApiMock = new ApiBrowserApiMock();
		spyOn(apiBrowserApiMock, 'getStatistics').and.returnValue({});
		spyOn(apiBrowserApiMock, 'getDetails').and.returnValue(details);

		TestBed.configureTestingModule({
			imports: [FormsModule],
			declarations: [ApiBrowserComponent],
			schemas: [NO_ERRORS_SCHEMA],
			providers: [
				{
					provide: ApiBrowserApi,
					useValue: apiBrowserApiMock
				},
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		})
			.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ApiBrowserComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('creates', () => {
		expect(component).toBeTruthy();
	});

	it('return select options', () => {
		expect(component.getOptions()).toEqual(details);
	});

	it('allows searching for entities', () => {
		const apiBrowserApiMockSearchSpy: jasmine.Spy = spyOn(apiBrowserApiMock, 'search').and.returnValue(new Promise((resolve) => {
			resolve({});
		}));

		fixture.debugElement.query(By.css('button[type=submit]')).nativeElement.click();
		fixture.detectChanges();

		expect(apiBrowserApiMock.search).toHaveBeenCalled();
		expect(apiBrowserApiMockSearchSpy.calls.argsFor(0)).toEqual(['AN', '', false]);
	});

	it('handles errorous response when searching for entities', () => {
		spyOn(apiBrowserApiMock, 'search').and.returnValue(new Promise((resolve, reject) => {
			reject({
				response: '{"error": true}'
			});
		}));

		fixture.debugElement.query(By.css('button[type=submit]')).nativeElement.click();
		fixture.detectChanges();

		return fixture.whenStable().then(() => {
			expect(component.hasError()).toBeTrue();
		});
	});

	describe('when response is received', () => {
		const respondWith = (response: any) => {
			spyOn(apiBrowserApiMock, 'search').and.returnValue(new Promise((resolve) => {
				resolve(response);
			}));

			fixture.debugElement.query(By.css('button[type=submit]')).nativeElement.click();
			fixture.detectChanges();
		};

		it('gets total results and have elements', () => {
			expect(component.getTotalResults()).toBe(0);
			expect(component.hasElements()).toBeFalse();

			respondWith({
				page: {
					totalElements: 5
				}
			});

			fixture.whenStable().then(() => {
				expect(component.getTotalResults()).toBe(5);
				expect(component.hasElements()).toBeTrue();
			});
		});

		it('gets return content', () => {
			const item = {};

			expect(component.hasContent()).toBeFalse();
			expect(component.hasMetadata()).toBeFalse();
			expect(component.getContent()).toEqual([]);

			respondWith({
				page: {
					totalElements: 5
				},
				content: [item]
			});

			fixture.whenStable().then(() => {
				expect(component.hasContent()).toBeTrue();
				expect(component.hasMetadata()).toBeTrue();
				expect(component.getContent()[0]).toBe(item);
			});
		});

		it('tells when there isn\'t more results than limit', () => {
			expect(component.hasMoreThanLimitResults()).toBeFalse();

			respondWith({
				page: {
					totalElements: 5
				},
				content: {}
			});

			fixture.whenStable().then(() => {
				expect(component.hasMoreThanLimitResults()).toBeFalse();
			});
		});

		it('tells when there isn\'t more results than limit', () => {
			expect(component.hasMoreThanLimitResults()).toBeFalse();

			respondWith({
				page: {
					totalElements: 55
				},
				content: {}
			});

			fixture.whenStable().then(() => {
				expect(component.hasMoreThanLimitResults()).toBeTrue();
			});
		});
	});

	it('gets item properties', () => {
		const properties = component.getProperties({
			test: 'value',
			another: 'prop'
		});

		expect(properties).toEqual([
			{
				key: 'test',
				value: 'value'
			},
			{
				key: 'another',
				value: 'prop'
			}
		]);
	});

	it('tells when value is scalar', () => {
		expect(component.valueIsScalar(0)).toBeTrue();
		expect(component.valueIsScalar(false)).toBeTrue();
		expect(component.valueIsScalar('test')).toBeTrue();
		expect(component.valueIsScalar({})).toBeFalse();
		expect(component.valueIsScalar([])).toBeFalse();
	});

	it('tells item is qualified item property', () => {
		expect(component.isQualifiedItemProperty({key: 'uid'})).toBeFalse();
		expect(component.isQualifiedItemProperty({key: 'other'})).toBeFalse();
		expect(component.isQualifiedItemProperty({key: 'uid', value: 'value'})).toBeFalse();
		expect(component.isQualifiedItemProperty({key: 'other', value: 'value'})).toBeTrue();
	});
});
