module.exports = function () {
  return {
    restrict:'EA',
    link: function (scope, element, attrs) {

      scope.$on('PROCESSING', function(val) {
        scope.processing = val;
      });
    }
  };
};