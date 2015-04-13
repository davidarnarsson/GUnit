module.exports = function ($location) {
  return {
    templateUrl: 'player-sessions.html',
    restrict: 'EA',
    scope: {
      sessions: '='
    },
    link: function (scope) {
      scope.showSession = function (sessionId) {
        $location.url("/session/" + sessionId);
      };
    }
  }
};