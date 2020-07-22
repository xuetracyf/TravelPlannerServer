Travel planner server

# prerequsites
## install mongodb server on your device
please follow official mongodb tutorial:
https://docs.mongodb.com/manual/installation/#mongodb-community-edition-installation-tutorials

## IDE: 
Recommend using Intellij: https://www.jetbrains.com/idea/download

Follow the installation guide to setup and set Java SDK (Recommend 1.8 version)

After cloning the repo, open the project with Intellij and add maven framework support:

1. Right click root folder (travel-planner-server)
2. Select the second item: Add Framework support
3. In the open window, select Maven option from the left tab and click Ok.

After that, install Lombok plugin:

1. Right click File Tab in your Intellij menu and click Setting (For Mac User click Intellij IDEA icon and select preference)
2. In the left tab, find Plugins and click
3. Search for Lombok and click Install button
4. Restart your Intellij

# Add Google Map API Key into your local application.property file
1. Get a Google Map API key: https://developers.google.com/maps/documentation/javascript/get-api-key
2. Create a directory named "config" under your project root directory
3. Create an application.property file under config folder
4. Add: "google.map.api.key={your Google Map Api Key}" (Don't include the quote) in your properties

# Configure your new property file in intellij
Follow official tutorial: https://www.jetbrains.com/help/idea/spring-boot.html#custom-configuration-files

# run in your local machine
1. First of all, run your local mongo database in default setting(localhost: 27017). Please refer to previous link guide to run your database: https://docs.mongodb.com/manual/installation/#mongodb-community-edition-installation-tutorials
2. You can run through your IDE or in your terminal: mvn spring-boot:run
