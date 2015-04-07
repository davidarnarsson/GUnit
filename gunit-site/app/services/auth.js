module.exports = function () {
  return {
    login: function(u) {
      localStorage.setItem("user", JSON.stringify(u));
    },
    isLoggedIn: function () {
      return !!localStorage.getItem("user");
    },
    logout: function() {
      localStorage.removeItem("user");
    },
    user: function() {
      return JSON.parse(localStorage.getItem("user"));
    }
  }
};
