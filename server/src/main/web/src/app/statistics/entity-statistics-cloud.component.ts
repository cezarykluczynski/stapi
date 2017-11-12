import { Component, OnInit } from '@angular/core';

import { StatisticsApi } from './statistics-api.service';
import { ApiBrowserApi } from '../api-browser/api-browser-api.service';

@Component({
	selector: 'entity-statistics-cloud',
	templateUrl: './entity-statistics-cloud.component.html',
	styleUrls: ['./entity-statistics-cloud.component.sass']
})
export class EntityStatisticsCloudComponent implements OnInit {

	private statisticsApi: StatisticsApi;
	private apiBrowserApi: ApiBrowserApi;
	private statistics: any;
	private details: any;
	private fictionalEntitites: Array<any>;
	private realWorldEntitites: Array<any>;
	private totalCount: number;
	private fontSizeMin = 18;
	private fontSizeMax = 35;

	constructor(statisticsApi: StatisticsApi, apiBrowserApi: ApiBrowserApi) {
		this.statisticsApi = statisticsApi;
		this.apiBrowserApi = apiBrowserApi;
	}

	ngOnInit() {
		this.statistics = this.statisticsApi.getStatistics();
		this.details = this.apiBrowserApi.getDetails();
		this.fictionalEntitites = this.createStatisticsForFictionalEntities();
		this.realWorldEntitites = this.createStatisticsForRealWorldEntities();
	}

	createStatisticsForFictionalEntities() {
		return this.getEntitiesOfType('FICTIONAL_PRIMARY');
	}

	createStatisticsForRealWorldEntities() {
		return this.getEntitiesOfType('REAL_WORLD_PRIMARY');
	}

	getStatisticsForFictionalEntities() {
		return this.fictionalEntitites;
	}

	getStatisticsForRealWorldEntities() {
		return this.realWorldEntitites;
	}

	getTotalCount() {
		return this.statistics.entitiesStatistics.totalCount;
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

}
