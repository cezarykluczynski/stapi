module.exports = function(grunt) {
	grunt.initConfig({
		clean: {
			options: {
				force: true
			},
			build: ['dist', '../resources/build', '../../../build/resources/main'],
		},
		copy: {
			main: {
				files: [
					{
						expand: true,
						src: '**',
						cwd: 'dist',
						dest: '../resources/build'
					},
					{
						expand: true,
						src: '**',
						cwd: 'dist',
						dest: '../../../build/resources/main/build'
					}
				],
			},
		}
	});

	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-copy');
};
