module.exports = function (sessionData, $scope, $api) {
  $scope.sessionData = sessionData.data;

  $scope.coverageData = null;

  $api.getCoverageData($scope.sessionData.session.sessionId).success(function (d) {
    $scope.coverageData = d;
  });
};