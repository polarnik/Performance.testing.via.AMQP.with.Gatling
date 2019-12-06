#!/bin/bash

rm -f *.jar

files=(\
"bcpkix-jdk15on.jar" \
"com.ibm.mq.commonservices.jar" \
"com.ibm.mq.jmqi.jar" \
"com.ibm.mq.tools.ras.jar" \
"jms.jar" \
"providerutil.jar" \
"bcprov-jdk15on.jar" \
"com.ibm.mq.headers.jar" \
"com.ibm.mqjms.jar" \
"com.ibm.mq.traceControl.jar" \
"org.json.jar" \
"com.ibm.mq.allclient.jar" \
"com.ibm.mq.jar" \
"com.ibm.mq.pcf.jar" \
"fscontext.jar")


docker stop ibmmqlibs

docker rm ibmmqlibs

docker run \
  -d \
  --name=ibmmqlibs \
  --env LICENSE=accept \
  --env MQ_QMGR_NAME=QM1 \
  ibmcom/mq:9.1.3.0


for f in "${files[@]}"
do
	echo "=================================================="
	echo "====== GET FILE: $f from docker container" 
    docker cp ibmmqlibs:/opt/mqm/java/lib/$f ./$f
	echo "====== GET FILE: $f from docker container complete" 

done

docker stop ibmmqlibs

docker rm ibmmqlibs

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./com.ibm.mq.commonservices.jar \
	-DgroupId=com.ibm.mq \
	-DartifactId=commonservices \
	-Dversion=9.1.3.0 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./com.ibm.mq.jar \
	-DgroupId=com.ibm.mq \
	-DartifactId=jmqi \
	-Dversion=9.1.3.0 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./com.ibm.mq.tools.ras.jar \
	-DgroupId=com.ibm.mq \
	-DartifactId=tools.ras \
	-Dversion=9.1.3.0 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./com.ibm.mq.allclient.jar \
	-DgroupId=com.ibm.mq \
	-DartifactId=allclient \
	-Dversion=9.1.3.0 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./com.ibm.mq.jar \
	-DgroupId=com.ibm.mq \
	-DartifactId=mq \
	-Dversion=9.1.3.0 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./com.ibm.mqjms.jar \
	-DgroupId=com.ibm.mq \
	-DartifactId=mqjms \
	-Dversion=9.1.3.0 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./fscontext.jar \
	-DgroupId=com.sun.jndi \
	-DartifactId=fscontext \
	-Dversion=1.2 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./providerutil.jar \
	-DgroupId=com.sun.jndi \
	-DartifactId=providerutil \
	-Dversion=1.2 \
	-Dpackaging=jar

mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file \
	-Dfile=./jms.jar \
	-DgroupId=javax.jms \
	-DartifactId=javax.jms-api \
	-Dversion=2.0 \
	-Dpackaging=jar