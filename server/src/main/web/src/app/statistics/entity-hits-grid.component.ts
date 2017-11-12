import { Component, OnInit, ChangeDetectionStrategy, ViewEncapsulation } from '@angular/core';

import {ColumnApi, GridApi, GridOptions} from 'ag-grid/main';

import { StatisticsApi } from './statistics-api.service';
import { ApiBrowserApi } from '../api-browser/api-browser-api.service';

@Component({
	selector: 'entity-hits-grid',
	templateUrl: './entity-hits-grid.component.html',
	styleUrls: [
		'../../../node_modules/ag-grid/src/styles/ag-grid.scss',
		'../../../node_modules/ag-grid/src/styles/theme-dark.scss',
		'./entity-hits-grid.component.sass',
	],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class EntityHitsGridComponent {

	private statistics: any;
	private details: any;
	private names: any;

	constructor(statisticsApi: StatisticsApi, apiBrowserApi: ApiBrowserApi) {
		this.statistics = statisticsApi.getStatistics();
		this.details = apiBrowserApi.getDetails();
		this.names = this.getEntityNameToPluralNameMap();
	}

	getGridColumnDefs() {
		return [
			{
				headerName: 'Resource name',
				field: 'resourceName',
				width: 268,
				pinned: 'left'
			},
			{
				headerName: 'Total number of hits',
				field: 'totalHits',
				width: 268,
				sort: 'desc',
				pinned: 'right'
			}
		];
	}

	getGridRowData() {
		const gridRowData: Array<any> = [];
		this.statistics.hitsStatistics.statistics.forEach((row) => {
			gridRowData.push({
				resourceName: this.names[row.name],
				totalHits: row.count
			});
		});
		return gridRowData;
	}

	getGridOptions() {
		return {
			enableSorting: true,
			enableColResize: false,
			suppressMovableColumns: true
		};
	}

	getTotalCount() {
		return this.statistics.hitsStatistics.totalCount;
	}

	getEntityNameToPluralNameMap() {
		const names = {};

		this.details.forEach((detail) => {
			names[detail.name] = detail.pluralName;
		});

		return names;
	}

}
