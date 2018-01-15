{{=<% %>=}}
{{!
This file contains a jar preamble script which will be
prepended to the resulting uber-jar and make it possible
to execute the jar as a normal system executable (without
java -jar) on both windows and *nix systems. 

The script below uses a special syntax which intermixes both sh
and cmd code. It is written this way because it is used in
system() shell-outs directly in otherwise portable code. See
https://stackoverflow.com/questions/17510688 for details.

This block is a moustache templating comment and will not be
included in the resulting jar preamble.

Please see https://github.com/BrunoBonacci/lein-binplus
for details on what templating expressions etc are possible for
the script below.

The script, as it stands, supports both direct java and drip jvm
accelerator invocations. In other words, when you just run your
custom command

$> bob

it will use the below script to check if 'drip' is available
on the path and if so, use it to launch the process. This only
works on osx and linux as I have not had the time to figure
out how to do this on windows.

-Matias 2018.01.11
}}
<%={{ }}=%>
:; hash drip  >/dev/null 2>&1 # make sure the command call on the next line has an up to date worldview
:;if command -v drip >/dev/null 2>&1 ; then CMD=drip; else CMD=java; fi
:;exec $CMD -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:-OmitStackTraceInFastThrow -client -Xbootclasspath/a:"$0" {{ns-name}}.core "$@"; exit $?
@echo off
java -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:-OmitStackTraceInFastThrow -client -Xbootclasspath/a:%1 {{ns-name}}.core "%~f0" %*
goto :eof
