#! /bin/sh


cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"




cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color
echo "${RED}\e[1mThis is the codegen tests for the byte part\e[0m"


: <<'END_COMMENT'

for fich in ./src/test/deca/codegen/valid/custom/byte/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")
  echo "$name"


  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/byte/ $name) || exit 1
  rm -f ./src/test/deca/codegen/valid/custom/byte/*.class


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done

echo "${GREEN}  This part is about the declarations\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/declaration/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/declaration/ $name) || exit 1
  rm -f $classFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done


echo "${GREEN}  This part is for the if and while instructions\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/if_while/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/if_while/ $name) || exit 1
  rm -f $classFile 2>/dev/null


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat

  fi

done




echo "${GREEN}  This part is for the booleans${NC}"
for fich in ./src/test/deca/codegen/valid/custom/operation_affectation/boolean/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/operation_affectation/boolean/ $name) || exit 1
  rm -f $classFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat

  fi

done


echo "${GREEN}  This part is for the operations on float\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/operation_affectation/float/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/operation_affectation/float/ $name) || exit 1
  rm -f $classFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done

echo "${GREEN}  This part is for the operations on integer\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/operation_affectation/int/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/operation_affectation/int/ $name) || exit 1
  rm -f $classFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done


echo "${GREEN}  This part is for the operations on class\n${NC}"
for fich in ./src/test/deca/codegen/valid/demo/clazz/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  echo "$classFile"
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/demo/clazz/ $name) || exit 1
  rm -f $classFile


  #echo $resultat | sed -e 's/\(.\)/\1\n/g'
  attendu=$(cat $resultFile)



  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done



echo "${GREEN}  This part is for the operations on objects\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/object/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/object/ $name) || exit 1
  rm -f $classFile



  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done

END_COMMENT



echo "${GREEN}  This part is for the include files\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/include/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/include/ $name) || exit 1
  rm -f $classFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done




done
echo "${GREEN}  This part is for the print instructions\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/print/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  classFile=$(echo "$fich" | sed "s/deca/class/g" | sed "s/class/deca/")
  name=$(basename "$fich" ".deca")

  rm -f $classFile 2>/dev/null
  decac -java $fich || exit 1
  if [ ! -f $classFile ]; then
      echo "Fichier .class non généré."
      exit 1
  fi

  resultat=$(java -cp ./src/test/deca/codegen/valid/custom/include/ $name) || exit 1
  rm -f $classFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done


