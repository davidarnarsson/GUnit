module.exports = function(leaderboard, $scope, $auth, statistics, $routeParams, $location, messages, $api, smells, $modal) {
  statistics = statistics.data;
  $scope.smells = smells.data;
  console.log(statistics);

  var msgs = [];
  messages.data.map(function (m) {
    var session = msgs.filter(function (x) { return x.sessionId === m.sessionId })[0];
    if (!session) {
      session = m.session;
      msgs.push(session);
    }
    session.messages = session.messages || [];
    session.messages.push(m);
  });

  $scope.messages = msgs;
  $scope.leaderboard = leaderboard.data;
  $scope.user = $auth.user();
  $scope.statistics = statistics;

  $scope.openModal = function (templateUrl) {
    var modalInstance = $modal.open({
      templateUrl: templateUrl
    });

  };

  $scope.viewDeveloper = function(userId) {
    $location.url('/user/' + userId);
  };

  $scope.showSession = function (sessionId) {
    $location.url("/session/" + sessionId);
  };

  this.currentPage = $routeParams.page || 1;

  $scope.setPage = function () {
    $api.getStatistics($scope.user.id, this.currentPage - 1).success(function (d) {
      $scope.statistics = d;
    });
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
      },
      {
        label: "Test Smells",
        fillColor: "rgba(111,127,165,0.2)",
        strokeColor: "rgba(111,127,165,1)",
        pointColor: "rgba(111,127,165,1)",
        pointStrokeColor: "#fff",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "rgba(111,127,165,1)",
        data: statistics.value.map(function(s) { return s.testSmells }).reverse()
      }
    ]
  };
};