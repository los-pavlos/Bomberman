@echo off
rem Set the directory to where the batch file is located
set CURRENT_DIR=%~dp0

rem Run the Java application using the paths to the JAR and libs inside the "target" folder
java --module-path "%CURRENT_DIR%target\project-0.0.1-SNAPSHOT.jar;%CURRENT_DIR%target\libs" -m cz.vsb.project/cz.vsb.App

rem Keep the command window open so the user can see any output or errors
pause