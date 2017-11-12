import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgGridModule } from 'ag-grid-angular/main';

import { EntityHitsGridComponent } from './entity-hits-grid.component';
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

describe('EntityHitsGridComponent', () => {
	let component: EntityHitsGridComponent;
	let fixture: ComponentFixture<EntityHitsGridComponent>;
	let statisticsApiMock: StatisticsApiMock;
	let apiBrowserApiMock: ApiBrowserApiMock;

	beforeEach(async(() => {
		statisticsApiMock = new StatisticsApiMock();
		apiBrowserApiMock = new ApiBrowserApiMock();

		TestBed.configureTestingModule({
			declarations: [EntityHitsGridComponent],
			providers: [
				{
					provide: StatisticsApi,
					useValue: statisticsApiMock
				},
				{
					provide: ApiBrowserApi,
					useValue: apiBrowserApiMock
				}
			],
			imports: [
				AgGridModule.withComponents([])
			]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(EntityHitsGridComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('creates', () => {
		expect(component).toBeTruthy();
	});

	it('gets grid column defs', () => {
		const columnDefs = component.getGridColumnDefs();
		expect(columnDefs.length).toBe(2);
		expect(columnDefs[0].field).toBe('resourceName');
		expect(columnDefs[0].pinned).toBe('left');
		expect(columnDefs[1].field).toBe('totalHits');
		expect(columnDefs[1].pinned).toBe('right');
	});

	it('gets grid row data', () => {
		expect(component.getGridRowData()).toEqual([
			{
				resourceName: 'astronomical objects',
				totalHits: 20
			},
			{
				resourceName: 'spacecraft classes',
				totalHits: 30
			},
			{
				resourceName: 'performers',
				totalHits: 35
			},
			{
				resourceName: 'episodes',
				totalHits: 20
			}
		]);
	});

	it('gets grid options', () => {
		expect(component.getGridOptions()).toEqual({
			enableSorting: true,
			enableColResize: false,
			suppressMovableColumns: true
		});
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
