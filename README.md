# G-Unit
## Gamification for unit testing
_____

#### Description

This is a gamification platform prototype which aims to add gamification elements to unit testing in an
industrial context.

#### Modules

 * gunit-maven-plugin - The Maven plugin which sends data to the gunit-service gamification service.
 * gunit-parent - Parent project which decides the order of building.
 * gunit-service - The gamification service.
 * gunit-core - The core gamification code and database interaction code
 * gunit-commons - Various common DTOs used throughout the system.
 * gunit-service-client - A client for interacting with the gunit service.
 * testhound-maven-plugin - A maven plugin which executes Michaela Greiler's TestHound test smell detecting tool
 * intelli-gunit - An intellij plugin which notifies a user of any newly processed data by the gamification server.


#### To-do

** Project **

 * Implement per-class branch coverage scoring rules
 * Implement socket communication between gunit-maven-plugin and intelli-gunit for relaying new test sessions
 * Make project more easily buildable/runnable/deployable.
 * News feed.
 * Player-targeted messages/events.
 * Quests

** Site **

 * Refactor the badge display into something more usable
 * Make more responsive
 * Migrate to a gulp-based project/own module?
 * Create a rule-result view per session.

#### License

MIT. Do what you want with it.
