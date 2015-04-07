module.exports = function () {
  return {
    restrict: 'EA',
    templateUrl: 'pager.html',
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
};