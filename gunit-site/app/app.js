

var angular = require('angular');
require('templates');
require('angular-route');
require('angular-bootstrap');

var app = angular.module("gunit", ['ngRoute', 'templates','ui.bootstrap']);
app.config(function ($routeProvider) {
  $routeProvider
    .when('/login', {
      templateUrl: 'login.html'
    })
    .when('/', {
      templateUrl: 'front-page.html',
      controller: 'FrontPageCtrl',
      resolve: {
        leaderboard: function ($api) {
          return $api.getLeaderboard();
        },
        messages: function ($api, $auth) {
          return $api.getMessages($auth.user().id);
        },
        statistics: function ($api, $auth, $route) {
          var page = parseInt($route.current.params.page, 10);
          if ($auth.isLoggedIn()) {
            return $api.getStatistics($auth.user().id, page || 0);
          }
          return null;
        }
      }
    }).
    when('/user/:id', {
      templateUrl: 'user.html',
      controller: 'UserDetailCtrl',
      resolve: {
        userData: function ($api,$route) {
          return $api.getUserById($route.current.params.id)
        },
        badges: function ($api, $route) {
          return $api.getUserBadges($route.current.params.id);
        }
      }
    }).
    when('/progress', {
      templateUrl: 'badges.html',
      controller: 'BadgesCtrl',
      controllerAs: 'ctrl',
      resolve: {
        badges: function($api) {
          return $api.getBadges();
        },
        rules: function ($api) {
          return $api.getRules();
        }
      }
    }).
    when('/session/:id', {
      templateUrl: 'session.html',
      controller: 'SessionCtrl',
      resolve: {
        sessionData: function ($api, $route) {
          return $api.getSession($route.current.params.id);
        }
      }
    })
    .otherwise({
      redirectTo:'/login'
    });
});

app.run(function($rootScope, $auth, $location, $processing) {
  $rootScope.$on('$routeChangeStart', function (evt, next, current) {
    if (next.$$route && !/^\/login/gi.test(next.$$route.originalPath) && !$auth.isLoggedIn()) {
      evt.preventDefault();
      //$processing.stop();
      $location.url('/login');
    } else {
     // $processing.start($auth.user().id);
      next.$$route.user = $auth.user();
    }
  });
});

app.factory('$api', require('./services/api'));
app.factory('$auth', require('./services/auth'));
app.factory('$processing', require('./services/processing'));

app.controller('BadgesCtrl', require('./controllers/BadgesCtrl'));
app.controller('LoginCtrl', require('./controllers/LoginCtrl'));
app.controller('UserCtrl', require('./controllers/UserCtrl'));
app.controller('FrontPageCtrl', require('./controllers/FrontPageCtrl'));
app.controller('SessionCtrl', require('./controllers/SessionCtrl'));
app.controller('UserDetailCtrl', require('./controllers/UserDetailCtrl'));

app.directive('processingSignal', require('./directives/processingSignal'));
app.directive('chart', require('./directives/chart'));
app.directive('userSelector', require('./directives/userSelector'));
app.directive('playerSessions', require('./directives/playerSessions'));
//app.directive('pager', require('./directives/pager'));

app.filter('coalesce', require('./filters/coalesce'));

module.exports = app;