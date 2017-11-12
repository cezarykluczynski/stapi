import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntityStatisticsCloudComponent } from './entity-statistics-cloud.component';

import { StatisticsApi } from './statistics-api.service';
import { ApiBrowserApi } from '../api-browser/api-browser-api.service';

import { TOTAL_COUNT, STATISTICS, DETAILS } from './statistics.fixture';

class StatisticsApiMock {
	public getStatistics() {
		return {
			entitiesStatistics: STATISTICS,
			hitsStatistics: STATISTICS
		};
	}
}

class ApiBrowserApiMock {
	public getDetails() {
		return DETAILS;
	}
}

describe('EntityStatisticsCloudComponent', () => {
	let component: EntityStatisticsCloudComponent;
	let fixture: ComponentFixture<EntityStatisticsCloudComponent>;
	let statisticsApiMock: StatisticsApiMock;
	let apiBrowserApiMock: ApiBrowserApiMock;

	beforeEach(async(() => {
		statisticsApiMock = new StatisticsApiMock();
		apiBrowserApiMock = new ApiBrowserApiMock();

		TestBed.configureTestingModule({
			declarations: [EntityStatisticsCloudComponent],
			providers: [
				{
					provide: StatisticsApi,
					useValue: statisticsApiMock
				},
				{
					provide: ApiBrowserApi,
					useValue: apiBrowserApiMock
				}
			]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(EntityStatisticsCloudComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('gets statistics for fictional entities', () => {
		expect(component.getStatisticsForFictionalEntities()).toEqual([
			{
				text: '30 spacecraft classes',
				weight: 30,
				fontSize: 35
			},
			{
				text: '20 astronomical objects',
				weight: 20,
				fontSize: 18
			}
		]);
	});

	it('gets statistics for real world entities', () => {
		expect(component.getStatisticsForRealWorldEntities()).toEqual([
			{
				text: '35 performers',
				weight: 35,
				fontSize: 35
			},
			{
				text: '20 episodes',
				weight: 20,
				fontSize: 18
			}
		]);
	});

	it('gets total count', () => {
		expect(component.getTotalCount()).toBe(TOTAL_COUNT);
	});

	it('calculates font size', () => {
		expect(component.calculateFontSize(100, 100, 200)).toBe(18);
		expect(component.calculateFontSize(300, 450, 600)).toBe(26.5);
		expect(component.calculateFontSize(300, 450, 600)).toBe(26.5);
		expect(component.calculateFontSize(100, 500, 500)).toBe(35);
	});

	it('gets entity name to plural name map', () => {
		expect(component.getEntityNameToPluralNameMap()).toEqual({
			AstronomicalObject: 'astronomical objects',
			SpacecraftClass: 'spacecraft classes',
			Performer: 'performers',
			Episode: 'episodes'
		});
	});
});
