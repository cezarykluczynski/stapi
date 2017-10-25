import { Component, OnInit } from '@angular/core';

import { EntityHitsGridComponent } from './entity-hits-grid.component';
import { EntityStatisticsCloudComponent } from './entity-statistics-cloud.component';

import { RestApiService } from '../rest-api/rest-api.service';

@Component({
	selector: 'app-statistics',
	templateUrl: './statistics.component.html',
	styleUrls: ['./statistics.component.sass']
})
export class StatisticsComponent {
}
