module.exports = function (grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        ts: {
            default : {
                tsconfig: './tsconfig.json',
                src : ['src-core/**/*.ts', 'src/**/*.ts'],
                out: 'src-gen/js/malai.js',
                passThrough: true
            }
        },
        clean: {
            folder: ['src-gen/js']
        }
    });

    grunt.loadNpmTasks("grunt-ts");
    grunt.registerTask("default", ["ts"]);
    grunt.loadNpmTasks('grunt-contrib-clean');
};
