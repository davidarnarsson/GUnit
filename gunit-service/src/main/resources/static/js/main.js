
var outerWrap = document.querySelector("#wrap");
if (outerWrap.style.height < window.innerHeight - 20) {
    outerWrap.style.minHeight = (window.innerHeight - 20) + 'px';
}

var module = angular.module("gunit", ['ngRoute']);

var serviceUrl = 'http://localhost:8080/api';

module.config(function ($routeProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: 'login.tpl'
        })
        .when('/', {
            templateUrl: 'front-page.tpl',
            controller: 'FrontPageCtrl',
            resolve: {
                leaderboard: function ($api) {
                    return $api.getLeaderboard();
                },
                statistics: function ($api, $auth, $route) {
                    var page = parseInt($route.current.params.page, 10);
                    if ($auth.isLoggedIn()) {
                        return $api.getStatistics($auth.user().id, page || 0);
                    }
                    return null;
                }
            }
        })
        .otherwise({
            redirectTo:'/login'
        });
});

module.run(function($rootScope, $auth, $location, $processing) {
    $rootScope.$on('$routeChangeStart', function (evt, next, current) {
        if (next.$$route && !/^\/login/gi.test(next.$$route.originalPath) && !$auth.isLoggedIn()) {
            evt.preventDefault();
            $processing.stop();
            $location.url('/login');
        } else {
            $processing.start($auth.user().id);
            next.$$route.user = $auth.user();
        }
    });
});

module.factory('$processing', function ($rootScope, $api) {
    var userId = 0;
    var intervalId = 0;
    return {
        start: function (u) {
            if (intervalId != 0) return;
            userId = u;
            intervalId = setInterval(function () {
                $api.getProcessing(userId).success(function (p) {
                    $rootScope.$broadcast('PROCESSING', p);
                });
            }, 1000);
        },
        stop: function () {
            clearInterval(intervalId);
        }
    };
});

module.factory('$api', function ($http) {
    return {
        getUser: function(name) {
            return $http.get(serviceUrl + '/users/by-name', {
                params: { name: name }
            });
        },
        getUsers: function() {
            return $http.get(serviceUrl + '/users');
        },
        getLeaderboard: function () {
            return $http.get(serviceUrl + '/game/leaderboard');
        },
        getStatistics: function (userId, page) {
            return $http.get(serviceUrl + '/statistics/by-user/' + userId, {
                params: { page: page }
            });
        },
        getProcessing: function (userId) {
            return $http.get(serviceUrl + '/statistics/processing/' + userId);
        }
    };
});

module.factory('$auth', function () {
    return {
        login: function(u) {
            localStorage.setItem("user", JSON.stringify(u));
        },
        isLoggedIn: function () {
            return !!localStorage.getItem("user");
        },
        logout: function() {
            localStorage.removeItem("user");
        },
        user: function() {
            return JSON.parse(localStorage.getItem("user"));
        }
    }
});

module.controller('UserCtrl', function ($auth) {
    this.getUser = $auth.user;
});

module.controller('LoginCtrl', function ($auth, $api, $location) {
    this.login = function(u, redirect) {
        $auth.login(u);
        $location.url("/");
    };

    this.getUsers = function () {
        return $api.getUsers();
    };

    this.isLoggedIn = function() {
        return $auth.isLoggedIn();
    };

    this.logout = function(url) {
        $auth.logout();
        $location.url(url);
    };
});

module.directive('userSelector', function () {
    return {
        require: '^ngModel',
        scope: {
            userPromise: '='
        },
        template: '<select ng-model="name" ng-options="k as k.name for k in users"> <option>Veldu</option> </select>',
        link: function (scope, element, attrs, ngModelCtrl) {
            scope.users = [];
            scope.userPromise().success(function (u) {
                scope.users = u;
            });

            scope.$watch('name', function (n) {
                if (!n) return;

                ngModelCtrl.$setViewValue(n);
            });
        }
    }
});

module.directive("chart", function () {
  return {
      scope: {
          data: '=',
          options: '='
      },
      restrict: 'EA',
      link: function (scope, element, attrs) {
          var canvas = element[0];
          var ctx = canvas.getContext('2d');
          var parent = canvas.parentElement;

          function onResize() {
              canvas.style.width = parent.clientWidth;
              canvas.style.height = canvas.style.width * 0.6;
          }

          onResize();

          scope.chart = new Chart(ctx).Line(scope.data, {responsive: true, scaleShowLabels: true ,
              legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"});
          element.parent().prepend(scope.chart.generateLegend());
      }
  };
});

module.controller('FrontPageCtrl', function(leaderboard, $scope, $auth, statistics, $routeParams, $location) {
    statistics = statistics.data;
    $scope.leaderboard = leaderboard.data;
    $scope.user = $auth.user();
    $scope.statistics = statistics;

    $scope.current = function () {
        return $routeParams.page;
    };

    $scope.setPage = function (p) {
      $location.search("page", p);
    };

    $scope.statisticsData = {
        labels: statistics.value.map(function (s) { return new Date(s.date).toLocaleDateString()}),
        datasets: [
            {
                label: 'Line Coverage',
                fillColor: "rgba(220,220,220,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: statistics.value.map(function(s) { return s.lineCoverage * 100 })
            },
            {
                label: "Branch Coverage",
                fillColor: "rgba(151,187,205,0.2)",
                strokeColor: "rgba(151,187,205,1)",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: statistics.value.map(function(s) { return s.branchCoverage * 100 })
            },
            {
                label: "Instruction Coverage",
                fillColor: "rgba(131,147,185,0.2)",
                strokeColor: "rgba(131,147,185,1)",
                pointColor: "rgba(131,147,185,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(131,147,185,1)",
                data: statistics.value.map(function(s) { return s.instructionCoverage * 100 })
            }
        ]
    };
});

module.directive('processingSignal', function () {
    return {
        restrict:'EA',
        link: function (scope, element, attrs) {

            scope.$on('PROCESSING', function(val) {
                console.log(val);
                scope.processing = val;
            });
        }
    };
});

module.directive('pager', function () {
    return {
        restrict: 'EA',
        templateUrl: 'pager.tpl',
        scope: {
            pageCount: '=',
            current: '=',
            setPage: '='
        },
        link: function (scope, element, attrs) {
            scope.pages = [];
            for (var i = 0; i < scope.pageCount; ++i) {
                scope.pages.push(i);
            }
        }
    };
});

function debounce(func, wait, immediate) {
    var timeout;
    return function() {
        var context = this, args = arguments;
        var later = function() {
            timeout = null;
            if (!immediate) func.apply(context, args);
        };
        var callNow = immediate && !timeout;
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
        if (callNow) func.apply(context, args);
    };
};