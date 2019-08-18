#!/usr/bin/env bash

echo "Building app..."

cd ..
sbt assembly
cd graal

# this script assumes that this file was already created
# with sbt-assembly
JAR_FILE=wif-assembly-0.1.jar
DIST_PATH=../dist

mkdir -p ${DIST_PATH}

echo "copying JAR file ..."
cp ../target/scala-2.12/${JAR_FILE} .

echo "running native-image ..."
# create a native image from the jar file and name
# the resulting executable `wif`
native-image --no-server --initialize-at-build-time=scala.Function0 -H:+ReportExceptionStackTraces -jar ${JAR_FILE} wif
cp wif ${DIST_PATH}
cp ${JAR_FILE} ${DIST_PATH}