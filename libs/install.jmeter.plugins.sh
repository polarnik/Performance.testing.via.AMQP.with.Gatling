#!/bin/bash


mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./plugins/gatling.acynclogplugin-0.0.1.jar \
	-DgroupId=io.github.qaload \
	-DartifactId=acynclogplugin \
	-Dversion=0.0.1 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./plugins/io.github.qaload-SharedHashMap-0.1.1.jar \
	-DgroupId=io.github.qaload \
	-DartifactId=SharedHashMap \
	-Dversion=0.1.1 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./plugins/zeph1rus-JMeterAMQP.jar \
	-DgroupId=com.zeroclue \
	-DartifactId=jmeter.amqp \
	-Dversion=1.0 \
	-Dpackaging=jar