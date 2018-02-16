import { Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ApiBrowserComponent } from './api-browser/api-browser.component';
import { ApiDocumentationComponent } from './api-documentation/api-documentation.component';
import { LicensingComponent } from './licensing/licensing.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PanelComponent } from './panel/panel.component';
import { ApiOverviewComponent } from './api-overview/api-overview.component';
import { LegalComponent } from './legal/legal.component';

export const APP_ROUTES: Routes = [
	{
		path: '',
		component: HomeComponent
	},
	{
		path: 'about',
		component: AboutComponent
	},
	{
		path: 'api-browser',
		component: ApiBrowserComponent
	},
	{
		path: 'api-documentation',
		component: ApiDocumentationComponent
	},
	{
		path: 'licensing',
		component: LicensingComponent
	},
	{
		path: 'statistics',
		component: StatisticsComponent
	},
	{
		path: 'panel',
		component: PanelComponent
	},
	{
		path: 'api-overview',
		component: ApiOverviewComponent
	},
	{
		path: 'privacy-policy',
		component: LegalComponent
	},
	{
		path: 'terms-of-service',
		component: LegalComponent
	},
	{
		path: '**',
		component: PageNotFoundComponent
	}
];
