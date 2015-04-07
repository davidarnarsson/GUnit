module.exports = function () {
  return function (v) {
    if (isNaN(v)) {
      return 0;
    }
    return v;
  }
};