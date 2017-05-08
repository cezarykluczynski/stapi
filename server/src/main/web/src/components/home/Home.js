import React, { Component } from 'react';
import './Home.css';
import { EntityCloudStatistics } from '../statistics/EntityCloudStatistics.js';

export class Home extends Component {

	render() {
		return (
			<div className='home'>
				<div className='row'>
					<div className='col-md-6'>
						<h2>Welcome to STAPI!</h2>
						<h3>But what is it, really?</h3>
						<ul>
							<li>The first public Star Trek API, accessible via REST and SOAP.</li>
							<li>An open source project, that anyone can <a
								href="https://github.com/cezarykluczynski/stapi/wiki/Contributing">contribute to</a>.</li>
							<li>Still an alpha version. There is lot of work of work behind, but a lot of <a
								href="https://github.com/cezarykluczynski/stapi/wiki/Work-progress">work ahead</a>, too.</li>
							<li>Learn more <a href="/about">here</a>.</li>
						</ul>
					</div>
					<div className='col-md-6'>
						<EntityCloudStatistics />
					</div>
				</div>
			</div>
		);
	}

}
