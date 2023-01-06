// karma.conf.js
const path = require('path');

module.exports = function(config) {
	config.set({
		basePath: '',
		frameworks: ['jasmine', '@angular-devkit/build-angular'],
		plugins: [
			'karma-jasmine',
			'karma-chrome-launcher',
			'karma-jasmine-html-reporter',
			'karma-junit-reporter',
			'karma-coverage',
			'@angular-devkit/build-angular/plugins/karma',
		],
		client: {
			clearContext: false, // leave Jasmine Spec Runner output visible in browser
		},
		jasmineHtmlReporter: {
			suppressAll: true, // removes the duplicated traces
		},
		coverageReporter: {
			dir: path.join(__dirname, 'coverage', './'),
			subdir: '.',
			reporters: [
				{ type: 'lcov' },
				{ type: 'text-summary' }
			],
		},
		junitReporter: {
			outputDir: 'junit-results/',
			outputFile: 'junit-xml-frontend-report.xml',
			suite: 'Frontend',
			useBrowserName: false,
		},
		reporters: ['progress', 'kjhtml', 'junit'],
		port: 9876,
		colors: true,
		logLevel: config.LOG_INFO,
		autoWatch: true,
		browsers: ['Chrome'],
		restartOnFileChange: true,
	});
};
