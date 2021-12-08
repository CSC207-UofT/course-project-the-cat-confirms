# ChatHub
course-project-the-cat-confirms created by GitHub Classroom

# Highlights since the demo
1. Architectural changes:
  * Interface Adapter are widely used to reduce dependencies between concrete classes\
  * (Boundary) If a use case class ever needs to depend on a gateway (UserProfile depends on UserRepo), an interface of the gateway is stored in the use case.
  * (Facade)Large classes have been refactored into smaller classes, either through static function in a helper class(HTTPHelper) or the Facade design pattern
  * (Clean Architure) is now stricly followed to avoid cross layer dependencies
2. Unit tests are added for most classes except the Server class
3. All classes are documented. Most controller/viewer/gateway classes are heavily documented

# Requirements
* Android SDK (Version 28 or higher)
* Node.js (Version 16 or higher)

# Building the web front end
Note these steps are also required for first-time setups:
```
cd web
npm install
npm run android-build
```
