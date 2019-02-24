# lein-cli-cmd
[![License](https://img.shields.io/badge/License-EPL%201.0-red.svg)](https://opensource.org/licenses/EPL-1.0) 
[![Current Version](https://img.shields.io/clojars/v/cli-cmd/lein-template.svg)](https://clojars.org/cli-cmd/lein-template)


lein-cli-cmd is a leiningen template for writing system commands in clojure. 

# Goals 
Remove all the boiler plate work in creating command line executables in clojure. You should run one command to create a well structured skeleton project and then be left with just implementing the actual core logic and defining the command line params. In Rich's words, we should remove as much of the incidental complexity as possible from the creation of cli tools in clojure. 

This project also creates executable jar files in the true sense of the word: 

> the jar file should be executable like a normal system command without the need to specify java -jar or any other magic stuff

# How Does This work? 
The above is accomplished using a jar preamble and rewriting of the jar file internal meta data so that the jar file is still considered a valid archive by java (and other tools) after the modification. 

The end result is that there is an embedded shell/bat script injected at the start of the jar file binary. As long as the resulting jar file is set to be executable by the operating system, you can then launch the jar file as a command, the embedded script will be executed and it will in essence execute `java -jar` on itself. 

In addition the embedded script uses the [drip](https://github.com/ninjudd/drip) jvm launcher if it is found on the system path (*nux only, windows not enabled yet). This significantly reduces startup times which matter when executing cli commands. 

For details on zip/jar rewriting, please refer to the projects this project depends on: 

* https://github.com/mbjarland/clj-zip-meta
* https://github.com/BrunoBonacci/lein-binplus

This projet will be updated as we come up with new ways of optimizing this process. 

# Installation 
This project has been deployed to clojars and you should not need to install anything. 

# Usage
The following command line sequence will create a new skeleton command line project, build the binary (`lein bin`) and execute it (`target/my-cmd`):

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

# Structure 
Creating a new cli project using this template will give you the following directory structure: 

```
.
├── boot
│   └── jar-preamble.sh
├── project.clj
├── src
│   └── mycommand
│       ├── cli.clj
│       └── core.clj
└── test
    └── mycommand
        └── core_test.clj

5 directories, 5 files
```

where the command line argument parsing logic and definition has been separated into its own namespace leaving core.clj relatively clean. 

# Dependencies 
The created project uses midje as a testing framework. This is a matter of taste and switching it out with something else is as easy as editing the generated project.clj 

# Customization 
The jar preamble script used is located in the `boot/jar-preamble.sh` file as depicted in the structure listing above. It can be modified and re-running `lein bin` will generate a new executable jar file with the modified preamble. 

Once built, the java version used by the command line script can be modified via: 

* if using drip - setting the DRIP_JAVA_CMD environment variable to point to a java executable 
* if not using drip - by setting the JAVA_CMD environment variable to point to a java executable, or if that is not found, by setting the JAVA_HOME environment variable to point to a jre or jdk installation. 
* if not using drip and no JAVA_CMD or JAVA_HOME is found, the command will default to running whatever `java` executable can be found on the system path. 

Please read the comment and code in the `boot/jar-preamble.sh` file for further details on this. 


## License

Copyright © 2019 Matias Bjarland

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
