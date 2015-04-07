
module.exports = function (badges, rules) {
  this.badges = badges.data;
  this.rules = rules.data;

  this.badgesById = {};
  this.badges.forEach(function (v) {
    this.badgesById[v.id] = v;
  }.bind(this));

  this.rules.map(function (x) {
    x.badge = this.badgesById[x.badgeId];
  }.bind(this));
};