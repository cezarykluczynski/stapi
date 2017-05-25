import React, { Component } from 'react';
import './About.css';

export class About extends Component {
	render() {
		return (
			<div className='about container content'>
				<div className="row">
					<div className="col-md-offset-3 col-md-6">

						<h3>The beginning</h3>
						<p>
							STAPI, a Star Trek API, was started in October 2016 by <a
							href="https://cezarykluczynski.com/">Cezary Kluczyński</a>. It was inspired by two projects: <a
							href="https://pokeapi.co/">Pokéapi</a> and <a href="https://swapi.co/">SWAPI, The Star Wars API</a>,
							both made by <a href="http://phalt.co/">Paul Hallett</a>.
						</p>
						<p>
							Initially, STAPI was just a research project. It aim was to check if building an API using Memory Alpha
							as a primary source of data is possible. Is it turned out, it was possible.
						</p>

						<h3>REST and SOAP</h3>
						<p>
							A second goal was to put out two versions of API, SOAP and REST. It been so since the project started.
						</p>

						<h3>Technologial stack</h3>
						<p>
							STAPI is written in Java and Groovy, using Spring as a primary framewrk. It's a fairly common technologial stack.
							<a href="https://github.com/cezarykluczynski/stapi/wiki/Contributing">Contributions</a> to STAPI
							are most welcome.
						</p>

						<h3>Alpha version</h3>
						<p>
							In the end of may 2017 an alpha version of STAPI has been put online. It contained 19 models,
							like locations, books, and astronomical objects. There was over 23000 entities initially available,
							including almost 6000 characters and more than 5000 performers.
						</p>

						<h3>Beta version</h3>
						<p>
							Beta version is planned for Q1 2018. As with many open source projects, that date could change.
						</p>
					</div>
				</div>
			</div>
		);
	}
}
