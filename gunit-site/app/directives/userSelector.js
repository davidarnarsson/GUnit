module.exports = function () {
  return {
    require: '^ngModel',
    scope: {
      userPromise: '='
    },
    template: '<select class="form-control" ng-model="name" ng-options="k as k.name for k in users"> <option>Veldu</option> </select>',
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
};