"use strict";

module.exports = function(grunt) {

    require("load-grunt-tasks")(grunt);

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        concat: {
            js: {
                src: ['js/app.js', 'js/**/*.js'],
                dest: 'static/js/main.js'
            },
            css: {
                src: ['style/main.css', 'style/stylesheets/*.css'],
                dest: 'static/css/main.css'
            }
        },
        clean: ['static/css/*', 'static/js/*'],
        jshint: {
            files: ['js/app.js', 'js/**/*.js'],
            options: {
                globals: {
                    jQuery: true
                },
                force: true
            }
        },
        uglify: {
            my_target: {
                files: {
                    'static/js/main.min.js': ['static/js/main.js']
                }
            }
        }
    });

    // various grunt tasks
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');

    // grunt build
    grunt.registerTask('build', ['clean', 'concat', 'csslint', 'jshint']);

    // grunt
    grunt.registerTask('default', ['build']);
};