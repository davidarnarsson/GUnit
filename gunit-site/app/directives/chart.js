var Chart = require('chart.js');

module.exports = function () {
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

      scope.chart = new Chart(ctx).Line(scope.data, {responsive: true, scaleShowLabels: true ,
        legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"});
      element.parent().prepend(scope.chart.generateLegend());
    }
  };
}