#!/bin/sh

#Comment this line if you don't want the systray4j daemon to start
#together with Memoranda, e.g.
# a) You don't use KDE 
# b) You've installed Systray4J as a shared library 
# c) Systray4J daemon starts on your runlevel (or manually).
exec ./lib/kde/systray4jd&
export MEMORANDA_HOME=./data

LCP="./build/memoranda.jar:./lib/xom-1.0.jar:./lib/xercesImpl.jar:./lib/xmlParserAPIs.jar:./lib/nekohtml.jar:./lib/nekohtmlXni.jar:./lib/kunststoff.jar:./lib/systray4j.jar"

java -cp ${LCP} net.sf.memoranda.Start $1
