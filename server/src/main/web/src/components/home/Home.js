import React, { Component } from 'react';
import './Home.css';
import { Link } from 'react-router-dom';
import { EntityStatisticsCloud } from '../statistics/EntityStatisticsCloud.js';

export class Home extends Component {

	render() {
		return (
			<div className='container content home'>
				<div className='row'>
					<div className='col-md-6'>
						<h2>Welcome to STAPI, a Star Trek API!</h2>
						<h3>But what is it, really?</h3>
						<ul>
							<li>It's the first public Star Trek API, accessible via REST and SOAP.</li>
							<li>It's an open source project, that anyone can <a
								href="https://github.com/cezarykluczynski/stapi/wiki/Contributing">contribute to</a>.</li>
							<li>STAPI it's still an alpha version. There is lot of work of work behind, but a lot of <a
								href="https://github.com/cezarykluczynski/stapi/wiki/Work-progress">work ahead</a>, too.
								No API stability, in terms of contracts layout, database layout, as well as API availability,
								is guaranteed at this point.</li>
							<li>ETA for beta version is Q1 2018.</li>
						</ul>

						<h3>How it works?</h3>
						<ul>
							<li>STAPI uses publically available sources, mainly <a href="http://memory-alpha.wikia.com/">Memory Alpha</a>,
								to get it's data.</li>
							<li>Data is cleansed, standarized and put into relational model.</li>
							<li>API can be browsed by anyone, even right now, using <Link to="/api-browser">API Browser</Link>.</li>
							<li>SOAP contracts and Swagger specifications can be <Link to="/api-documentation">downloaded</Link>.</li>
							<li>STAPI is written in Java and Groovy.</li>
						</ul>

						<h3>Learn more!</h3>
						<ul>
							<li>Read the <Link to="/about">about</Link> page.</li>
							<li>Browse the <Link to="/api-documentation">API documentation</Link>.</li>
							<li>Browse the <a href="https://github.com/cezarykluczynski/stapi/wiki">project documentation</a>.</li>
						</ul>
					</div>
					<div className='col-md-6'>
						<EntityStatisticsCloud />

						<hr />
						<h3>Project status</h3>
						<p className='project-status'>
							<a href="https://semaphoreci.com/cezarykluczynski/stapi"><img src="https://semaphoreci.com/api/v1/cezarykluczynski/stapi/branches/master/badge.svg" alt="Build Status"/></a>
							<a href="https://codecov.io/gh/cezarykluczynski/stapi"><img src="https://codecov.io/gh/cezarykluczynski/stapi/branch/master/graph/badge.svg" alt="codecov"/></a>
							<a href="https://www.codacy.com/app/cezarykluczynski/stapi"><img src="https://api.codacy.com/project/badge/Grade/171b4810a7334c5fa331881212837d34" alt="Codacy Badge"/></a>
							<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License: MIT"/></a>
						</p>
					</div>
				</div>
			</div>
		);
	}

}
