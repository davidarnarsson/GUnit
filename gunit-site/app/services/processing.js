module.exports = function ($rootScope, $api) {
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
};