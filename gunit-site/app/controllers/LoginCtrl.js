
module.exports = function ($auth, $api, $location) {
  this.login = function(u, redirect) {
    $auth.login(u);
    $location.url("/");
  };

  this.getUsers = function () {
    return $api.getUsers();
  };

  this.isLoggedIn = function() {
    return $auth.isLoggedIn();
  };

  this.logout = function(url) {
    $auth.logout();
    $location.url(url);
  };
};