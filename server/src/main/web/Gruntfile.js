module.exports = function(grunt) {
	grunt.initConfig({
		clean: {
			options: {
				force: true
			},
			build: ['build', '../resources/build'],
		},
		copy: {
			main: {
				files: [
					{
						expand: true,
						src: ['build/**'],
						dest: '../resources/'
					},
				],
			},
		}
	});

	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-copy');
};
