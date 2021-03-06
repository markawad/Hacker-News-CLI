# Hacker News Client

The main idea behind this project, is to fetch useful information from https://news.ycombinator.com through the command line. This is done with the help of the Hacker News API - https://github.com/HackerNews/API

## Available Commands
```
Usage: hackernewsclient [options] [command] [command-options]

[options]
--help                      Displays the help page and info on how to use the cli
-h                          Same as --help
--ttl=600                   Sets the time to live value. (Default is 600)
clear-cache                 clears the existing cache

[command]
new-stories                 Displays the newest stories added to hackernews cli
top-stories                 Displays the top stories added to hackernews cli
best-stories                Displays the best stories added to hackernews cli
--user=exampleUser          When presented alone, displays the user's profile
--display-comments=12345    Displays the comments of any valid item id. (Hence able to display multiple layers)

[command-options]
--display-stories           Displays the stories of the presented user
--page=1                    Sets which page you would like to browse. (Default is 1)
--size=5                    Sets the page size of the items being displayed. (Default is 5)


cli examples:
hackernewscli top-stories --page=3 --size=10
hackernewscli --user=examp11
hackernewscli --user=examp11 --display-stories --page=2 --size=5
hackernewscli --display-comments=12345

```

## Build

The project can be build either using IntelliJ Idea directly or by using the following `sbt` command:

```
sbt assembly
```

This command compiles the entire code, runs the tests and produces a jar including all dependencies.

## Usage

Same as building, the project can be run through the IntelliJ Idea; by running the sbt command:

```
sbt "run top-stories --page=2"
```

or, after building with the `sbt assembly`, by running the prepared executable script:

```
./hnc top-stories --page=2
```
