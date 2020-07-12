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

1. Right click File Tab in your Intellij menu and click Setting
2. In the left tab, find Plugins and click
3. Search for Lombok and click Install button
4. Restart your Intellij

# run
1. First of all, run your local mongo database in default setting(localhost: 27017). Please refer to previous link guide to run your databse: https://docs.mongodb.com/manual/installation/#mongodb-community-edition-installation-tutorials
2. Run SpringBoot Application by right click TravelplannerServerApplication file under src/main/java folder. Select "Run TravelplannerServerAppliction".
