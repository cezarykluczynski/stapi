import React, { Component } from 'react';
import './Home.css';

export class Home extends Component {

	render() {
		return (
			<div className='home'>
				<h2>Welcome to STAPI!</h2>
				<h3>But what is it, really?</h3>
				<ul>
					<li>The first public Star Trek API, accessible via REST and SOAP.</li>
					<li>An open source project, that anyone can <a
						href="https://github.com/cezarykluczynski/stapi/wiki/Contributing">contribute to</a>.</li>
					<li>Still an alpha version. A lot of work of work behind, and a lot of <a
						href="https://github.com/cezarykluczynski/stapi/wiki/Work-progress">work ahead</a>.</li>
					<li>There are <a href="https://github.com/cezarykluczynski/stapi/wiki/Clients">clients</a> needed
					for different languages, so more people can use STAPI.</li>
				</ul>
			</div>
		);
	}

}
