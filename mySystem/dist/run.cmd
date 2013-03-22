echo "ÇëÎð¹Ø±Õ£¡£¡£¡"
echo off
set JAVA_HOME=./jdk170_09
set PATH=./jdk170_09/bin;%PATH%
set CLASSPATH=./lib/log4j.jar;./apps/SerialDemo.jar;./apps/ParallelBlackBox.jar;./j2sdk1.4.2_16/lib/comm.jar;.;%CLASSPATH%

java -cp ./*.properties;%CLASSPATH%;. -jar sendmsg.jar
