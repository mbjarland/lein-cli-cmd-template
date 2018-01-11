# lein-cli-cmd
lein-cli-cmd is a leiningen plugin for creating "cli command" projects.

# Installation 
Check out this project, cd into the root and issue `lein install`

# Prerequisites
This project requires a thusfar unreleased version of the `lein-bin` project. Please install this manually by checking out: 

https://github.com/mbjarland/lein-bin

and executing: 

```
$> cd lein-bin
$> lein install 
```


# Usage
Issue: 

```bash
# create the cli command project
$> lein new lein-cli-cmd my-cmd

# cd into the newly created project dir
$> cd my-cmd

# the below will generate and executable jar which does 
# not require java -jar
$> lein bin 
lein bin 
Compiling my-cmd.core
Compiling my-cmd.core
Created /home/mbjarland/projects/iteego.toolbox/projects/tmp/my-cmd/target/my-cmd-0.1.0-SNAPSHOT.jar
Created /home/mbjarland/projects/iteego.toolbox/projects/tmp/my-cmd/target/my-cmd-0.1.0-SNAPSHOT-standalone.jar
Creating standalone executable: /home/mbjarland/projects/iteego.toolbox/projects/tmp/my-cmd/target/my-cmd
> re-aligning zip offsets...

# and finally execute the newly generated executable command
# this requires that the 'java' command be on the command line 
# and additionally, if the 'drip' command is on the command line
# the execution will be much faster
$> target/my-cmd 
This is my program. There are many like it, but this one is mine.

Usage: program-name [options] action

Options:
  -p, --port PORT      80         Port number
  -H, --hostname HOST  localhost  Remote host
      --detach                    Detach from controlling process
  -v                              Verbosity level; may be specified multiple times to increase value
  -h, --help

Actions:
  start    Start a new server
  stop     Stop an existing server
  status   Print a servers status

Please refer to the manual page for more information.

$> 
```