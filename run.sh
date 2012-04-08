#!/bin/sh
javac -Xlint "$1.java" && java -Djava.security.policy=wideopen.policy $1
