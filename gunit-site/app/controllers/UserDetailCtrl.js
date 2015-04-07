module.exports = function(userData, badges, $scope) {
  $scope.user = userData.data;
  $scope.badges = badges.data;
};