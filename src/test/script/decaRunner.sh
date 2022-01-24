#! /bin/sh
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"


decac src/test/deca/codegen/valid/demo/clazz/for_runner.deca

javac src/test/java/fr/ensimag/deca/tools/TestDecaRunner.java

java src/test/java/fr/ensimag/deca/tools/TestDecaRunner