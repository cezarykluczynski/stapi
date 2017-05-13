import React, { Component } from 'react';
import './Home.css';
import { Link } from 'react-router-dom';
import { EntityStatisticsCloud } from '../statistics/EntityStatisticsCloud.js';

export class Home extends Component {

	render() {
		return (
			<div className='home'>
				<div className='row'>
					<div className='col-md-6'>
						<h2>Welcome to STAPI!</h2>
						<h3>But what is it, really?</h3>
						<ul>
							<li>It's the first public Star Trek API, accessible via REST and SOAP.</li>
							<li>It's an open source project, that anyone can <a
								href="https://github.com/cezarykluczynski/stapi/wiki/Contributing">contribute to</a>.</li>
							<li>STAPI it's still an alpha version. There is lot of work of work behind, but a lot of <a
								href="https://github.com/cezarykluczynski/stapi/wiki/Work-progress">work ahead</a>, too.</li>
							<li>ETA for beta version is Q1 2018.</li>
						</ul>

						<h3>How it works?</h3>
						<ul>
							<li>STAPI uses publically available sources, mainly <a href="http://memory-alpha.wikia.com/">Memory Alpha</a>,
								to get it's data.</li>
							<li>Data is cleaned, standarized and put into relational model.</li>
							<li>API can be browsed by anyone, even right now, using <Link to="/api-browser">API Browser</Link>.</li>
							<li>STAPI is written in Java and Groovy.</li>
						</ul>

						<h3>Learn more!</h3>
						<ul>
							<li>Read the <Link to="/about">about</Link> page.</li>
							<li>Browse the <Link to="/documentation">API documentation</Link>.</li>
							<li>Browse the <a href="https://github.com/cezarykluczynski/stapi/wiki">project documentation</a>.</li>
						</ul>
					</div>
					<div className='col-md-6'>
						<EntityStatisticsCloud />
					</div>
				</div>
			</div>
		);
	}

}
