#! /bin/sh

DECAC_HOME=$(cd "$(dirname "$0")"/../../../../ && pwd)
GREEN='\033[0;32m'
NC='\033[0m'

file=$1

cat $file.deca

read break

echo ${GREEN} decac $file.deca ${NC}
decac $file.deca

if [ $file = "error/impossible_cast_up_down" ]
then
  read break
  echo ${GREEN} ima $file.ass ${NC}
  echo
  ima $file.ass
  exit 0
fi

if [ $file = "error/over_flow_error" ]
then
  read break
  echo ${GREEN} ima $file.ass ${NC}
  echo
  ima $file.ass
  exit 0
fi

if [ $file = "sans_objet/factorial" ]
then
  read break
  echo ${GREEN} ima $file.ass ${NC}
  echo
  ima $file.ass
  exit 0
fi

if [ $file = "sans_objet/implicit_initialisation" ]
then
  read break
  echo ${GREEN} ima $file.ass ${NC}
  echo
  ima $file.ass
  exit 0
fi

