

var serviceUrl = '/api';

module.exports = function ($http) {
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
    },
    getBadges: function() {
      return $http.get(serviceUrl + '/badges');
    },
    getRules: function() {
      return $http.get(serviceUrl + '/rules');
    },
    getLatestSmells: function(userId) {
      return $http.get(serviceUrl + '/smells/'+ userId);
    }
  };
};
