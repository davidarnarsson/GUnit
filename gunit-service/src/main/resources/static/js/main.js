
var outerWrap = document.querySelector("#wrap");
if (outerWrap.style.height < window.innerHeight - 20) {
    outerWrap.style.minHeight = (window.innerHeight - 20) + 'px';
}

var module = angular.module("gunit", ['ngRoute']);

var serviceUrl = '/api';

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
            templateUrl: 'user.tpl',
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
        when('/session/:id', {
            templateUrl: 'session.tpl',
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
        getUserById: function (id) {
          return $http.get(serviceUrl + '/users/' + id);
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
        getSession: function (sessionId) {
            return $http.get(serviceUrl + '/statistics/' + sessionId);
        },
        getProcessing: function (userId) {
            return $http.get(serviceUrl + '/statistics/processing/' + userId);
        },
        getUserBadges: function(userId) {
            return $http.get(serviceUrl + '/users/' + userId + '/badges');
        },
        getMessages: function (userId) {
          return $http.get(serviceUrl + '/users/' + userId + '/messages')
        },
        getCoverageData: function (sessionId) {
            return $http.get(serviceUrl + '/coverage/session/' + sessionId);
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

module.controller('FrontPageCtrl', function(leaderboard, $scope, $auth, statistics, $routeParams, $location, messages) {
    statistics = statistics.data;
    $scope.messages = messages.data;
    $scope.leaderboard = leaderboard.data;
    $scope.user = $auth.user();
    $scope.statistics = statistics;

    $scope.viewDeveloper = function(userId) {
        $location.url('/user/' + userId);
    };

    $scope.showSession = function (sessionId) {
        $location.url("/session/" + sessionId);
    };

    $scope.current = function () {
        return $routeParams.page;
    };

    $scope.setPage = function (p) {
      $location.search("page", p);
    };

    $scope.statisticsData = {
        labels: statistics.value.map(function (s) { return new Date(s.date).toLocaleDateString()}).reverse(),
        datasets: [
            {
                label: 'Line Coverage',
                fillColor: "rgba(220,220,220,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: statistics.value.map(function(s) { return s.lineCoverage * 100 }).reverse()
            },
            {
                label: "Branch Coverage",
                fillColor: "rgba(151,187,205,0.2)",
                strokeColor: "rgba(151,187,205,1)",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: statistics.value.map(function(s) { return s.branchCoverage * 100 }).reverse()
            },
            {
                label: "Instruction Coverage",
                fillColor: "rgba(131,147,185,0.2)",
                strokeColor: "rgba(131,147,185,1)",
                pointColor: "rgba(131,147,185,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(131,147,185,1)",
                data: statistics.value.map(function(s) { return s.instructionCoverage * 100 }).reverse()
            }
        ]
    };
});

module.directive('processingSignal', function () {
    return {
        restrict:'EA',
        link: function (scope, element, attrs) {

            scope.$on('PROCESSING', function(val) {
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

module.controller('UserDetailCtrl', function(userData, badges, $scope) {
    $scope.user = userData.data;
    $scope.badges = badges.data;
});

module.controller('SessionCtrl', function (sessionData, $scope, $api) {
    $scope.sessionData = sessionData.data;

    $scope.coverageData = null;

    $api.getCoverageData($scope.sessionData.session.sessionId).success(function (d) {
        $scope.coverageData = d;
    });
});

module.factory('$tabService', function () {
  function serialize(content) {
    localStorage.setItem("$tabs", JSON.stringify(content));
  }

  function deserialize() {
    return JSON.parse(localStorage.getItem("$tabs"));
  }

  return {
    getActiveTab: function (tabsId) {
      var ctx = deserialize();
      if (ctx && ctx[tabsId]){
        return ctx[tabsId];
      }
      return null;
    },
    setActiveTab: function (tabsId, tab) {
      var ctx = deserialize() || {};
      ctx[tabsId] = tab;
      serialize(ctx);
    }
  };
});

module.directive('tabs', function ($location, $tabService) {
  return {
    restrict: 'E',
    templateUrl: 'tabs.tpl',
    scope: {
      id: '@'
    },
    transclude: true,
    controllerAs:'ctrl',
    controller: function ($scope) {
      this.tabs = {};

      this.activeTab = $tabService.getActiveTab($scope.id);

      this.addTab = function (tab, scope) {
        this.tabs[tab] = scope;

        if(this.activeTab == null) {
          this.activeTab = tab;
        }
      }.bind(this);

      this.setActive = function(t) {
        $tabService.setActiveTab($scope.id, t);
        this.activeTab = t;
      }.bind(this);
    }
  };
});

module.directive('tab', function () {
  return {
    restrict: 'E',
    require: '^tabs',
    templateUrl: 'tab.tpl',
    transclude: true,
    scope: {
      title: '@'
    },
    link: function (scope, element, attrs, TabsCtrl) {
      TabsCtrl.addTab(scope.title, scope);

      scope.$watch(function() { return TabsCtrl.activeTab == scope.title}, function(b) {
        scope.active = b;
      });
    }
  };
});

module.filter('coalesce', function () {
   return function (v) {
       if (isNaN(v)) {
           return 0;
       }
       return v;
   }
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
}
