## Welcome
This application is a Kanban style study tool written in JavaFX

#### Screenshot:
![screenshot](https://cloud.githubusercontent.com/assets/1751112/24832405/6d330604-1c7d-11e7-990e-3a7fcb989899.PNG)

#### Instructions for use
Install Gradle and Git on your system. Make the following changes once:

```
$ git config --global user.name [your username]
```
This command will set the username that will appear on your commit transactions.


```
$ git config --global user.email [email address]
```
This command will set the email of your commit transactions.

#### Create a new repository

```
$ git init
```
With `git init`, this project can be a new one (that doesn’t have anything on it) or it can be a project that already exists but you want to use version control on it. 
You should run this command on the folder of your project.

```
$ git clone https://github.com/adwansyed/SystemsIntegrationProject.git
```
With `git clone`, you will download a copy of this Git project that already exists. This will create a new folder with all the projects on it, in the current directory.

#### Select master branch for production version

```
$ git checkout [branch-name]
```
This command will switch from the branch that you are in to the one specified.

#### Build and run
Under root folder in terminal interface issue following commands
```
gradle classes
gradle build
gradle run

[OPTIONAL for building and running JAR]: 
gradle jar
java -jar build/libs/SystemsIntegrationProject-1.0.jar
```
