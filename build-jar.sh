#!/bin/bash

mvn clean package -P test -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
