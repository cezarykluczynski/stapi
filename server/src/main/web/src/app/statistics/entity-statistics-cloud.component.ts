import { Component, OnInit } from '@angular/core';

import { StatisticsApi } from './statistics-api.service';
import { ApiBrowserApi } from '../api-browser/api-browser-api.service';
import {ApiDocumentationApi} from '../api-documentation/api-documentation-api.service';

@Component({
	selector: 'entity-statistics-cloud',
	templateUrl: './entity-statistics-cloud.component.html',
	styleUrls: ['./entity-statistics-cloud.component.sass']
})
export class EntityStatisticsCloudComponent implements OnInit {

	private statisticsApi: StatisticsApi;
	private apiBrowserApi: ApiBrowserApi;
	private apiDocumentationApi: ApiDocumentationApi;
	private statistics: any;
	private details: any;
	private fictionalEntities: Array<any>;
	private realWorldEntities: Array<any>;
	private totalCount: number;
	private fontSizeMin = 18;
	private fontSizeMax = 35;
	private dataVersion: String;

	constructor(statisticsApi: StatisticsApi, apiBrowserApi: ApiBrowserApi, apiDocumentationApi: ApiDocumentationApi) {
		this.statisticsApi = statisticsApi;
		this.apiBrowserApi = apiBrowserApi;
		this.apiDocumentationApi = apiDocumentationApi;
	}

	ngOnInit() {
		this.statistics = this.statisticsApi.getStatistics();
		this.details = this.apiBrowserApi.getDetails();
		this.fictionalEntities = this.createStatisticsForFictionalEntities();
		this.realWorldEntities = this.createStatisticsForRealWorldEntities();
		this.dataVersion = this.apiDocumentationApi.getDataVersion();
	}

	createStatisticsForFictionalEntities() {
		return this.getEntitiesOfType('FICTIONAL_PRIMARY');
	}

	createStatisticsForRealWorldEntities() {
		return this.getEntitiesOfType('REAL_WORLD_PRIMARY');
	}

	getStatisticsForFictionalEntities() {
		return this.fictionalEntities;
	}

	getStatisticsForRealWorldEntities() {
		return this.realWorldEntities;
	}

	getTotalCount() {
		return this.statistics.entitiesStatistics.totalCount;
	}

	getRelationsCount() {
		return this.statistics.entitiesStatistics.relationsCount;
	}

	getEntitiesOfType(type) {
		const entitiesNames = this.details
			.filter(entity => entity.type === type)
			.map(entity => entity.name);

		const entities = this.statistics.entitiesStatistics.statistics.filter((item) => {
			return entitiesNames.includes(item.name);
		});

		const names = this.getEntityNameToPluralNameMap();
		const sizes: Array<Number> = [];

		entities.sort((left, right) => {
			sizes.push(left.count);
			sizes.push(right.count);
			return left.count < right.count ? 1 : (left.count === right.count ? 0 : -1);
		});

		const min: number = Math.min.apply(null, sizes);
		const max: number = Math.max.apply(null, sizes);

		return entities.map((entity) => {
			return {
				text: entity.count + ' ' + names[entity.name],
				weight: entity.count,
				fontSize: this.calculateFontSize(min, entity.count, max)
			};
		});
	}

	calculateFontSize(min: number, current: number, max: number): number {
		const diff: number = this.fontSizeMax - this.fontSizeMin;
		const diff2: number = (current - min) / (max - min);
		return diff * diff2 + this.fontSizeMin;
	}

	getEntityNameToPluralNameMap() {
		const names = {};

		this.details.forEach((detail) => {
			names[detail.name] = detail.pluralName;
		});

		return names;
	}

	hasDataVersion() {
		return this.dataVersion != null && this.dataVersion.length;
	}

	getDataVersion() {
		if (!this.hasDataVersion()) {
			return ' at unknown time';
		}
		const dataVersionParts = this.dataVersion.split('-');
		const year = dataVersionParts[0];
		const month = this.getMonth(Number.parseInt(dataVersionParts[1]));
		return 'in ' + month + ' ' + year;
	}

	getMonth(number: number) {
		switch (number) {
			case 1: return 'January';
			case 2: return 'February';
			case 3: return 'March';
			case 4: return 'April';
			case 5: return 'May';
			case 6: return 'June';
			case 7: return 'July';
			case 8: return 'August';
			case 9: return 'September';
			case 10: return 'October';
			case 11: return 'November';
			case 12: return 'December';
		}
		return '';
	}

}
