/**
 * Created by davida on 4.4.2015.
 */
var gulp = require('gulp');
var merge = require('merge-stream');
var gutil = require('gulp-util');
var jshint = require('gulp-jshint');
var uglify = require('gulp-uglify');
var less = require('gulp-less');
var browserify = require('gulp-browserify');
var concat = require('gulp-concat');
var clean = require('gulp-rimraf');
var livereload = require('gulp-livereload');
var plumber = require('gulp-plumber');
var connect = require('gulp-connect');
var templateCache = require('gulp-angular-templatecache');

var destination = 'build';

var jsHintConfig = {
  "node": true
};

gulp.task('lint', function () {
  return gulp.src('./app/**/*.js')
    .pipe(plumber())
    .pipe(jshint(jsHintConfig))
    .pipe(jshint.reporter('default'));
});


gulp.task('styles', function () {
  return gulp.src('./app/styles/main.less')
    .pipe(plumber())
    .pipe(less({
      paths: ['bower_components/bootstrap/less']
    }))
    .pipe(concat('app.css'))
    .pipe(gulp.dest(destination + '/css'))
    .pipe(connect.reload());
});

gulp.task('clean', function () {
  return gulp.src('./build', { read: false })
    .pipe(plumber())
    .pipe(clean());
});

var environment = gulp.env.config ? gulp.env.config : "dev";

gulp.task('browserify', ['templates'], function () {
  return gulp.src('./app/app.js')
    .pipe(plumber())
    .pipe(browserify({
      insertGlobals : false,
      debug : environment === "dev",
      shim: {
        'templates': {
          path: 'app/templates/templates.js',
          exports: 'angular',
          depends: {
            angular: 'angular'
          }
        }
      }
    }))
    .pipe(concat('app.js'))
    .pipe(gulp.dest(destination + '/js'))
    .pipe(connect.reload());
});

gulp.task('uglify', ['browserify'], function() {
  return gulp.src(destination + '/js/app.js')
    .pipe(uglify())
    .pipe(gulp.dest(destination + '/js'));
});


gulp.task('templates', function (cb) {
  return gulp.src('app/templates/**/*.html')
    .pipe(templateCache({ standalone: true, module: 'templates' }))
    .pipe(gulp.dest('app/templates'))
    .pipe(connect.reload());
});

gulp.task('assets', function () {
  var html = gulp.src('assets/**.html')
    .pipe(gulp.dest(destination))
    .pipe(connect.reload());
  var fonts = gulp.src('assets/fonts')
    .pipe(gulp.dest(destination));
  var fonts2 = gulp.src('assets/fonts/**')
    .pipe(gulp.dest(destination + '/fonts'));
  return merge(html, fonts,fonts2);
});

gulp.task('connect' ,function() {
  connect.server({root: 'build', livereload: true, port: 3000 });
});

gulp.task('do-watch', function () {

  gulp.watch(['app/templates/**/*.html', 'app/**/*.js', '!app/templates/templates.js'], ['browserify','lint']);

  gulp.watch('app/styles/**/*.less', ['styles']);

  gulp.watch('assets/**', ['assets']);
});


gulp.task('default', ['templates', 'browserify', 'styles', 'assets', 'lint']);
gulp.task('watch', ['default', 'connect', 'do-watch']);