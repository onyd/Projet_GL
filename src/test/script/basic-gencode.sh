#! /bin/sh

# Auteur : gl28
# Version initiale : 01/01/2022



cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color
echo "${RED}\e[1mLets test the custom tests for the codegen part\e[0m"





echo "${GREEN}  This part is about the declarations\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/declaration/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile


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
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile 2>/dev/null


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
      exit 1
  fi

done


echo "${GREEN}  This part is for the booleans${NC}"
for fich in ./src/test/deca/codegen/valid/custom/operation_affectation/boolean/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
      exit 1
  fi

done


echo "${GREEN}  This part is for the operations on float\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/operation_affectation/float/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile


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
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile


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
for fich in ./src/test/deca/codegen/valid/demo/class/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile


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
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done


echo "${GREEN}  This part is for the include files\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/include/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done


echo "${GREEN}  This part is for the invalid files\n${NC}"
for fich in ./src/test/deca/codegen/invalid/custom/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile)
  rm -f $assFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done
echo "${GREEN}  This part is for the print instructions\n${NC}"
for fich in ./src/test/deca/codegen/valid/custom/print/*.deca
do

  resultFile=$(echo "$fich" | sed "s/deca/res/g" | sed "s/res/deca/")
  assFile=$(echo "$fich" | sed "s/deca/ass/g" | sed "s/ass/deca/")

  rm -f $assFile 2>/dev/null
  decac $fich || exit 1
  if [ ! -f $assFile ]; then
      echo "Fichier .ass non généré."
      exit 1
  fi

  resultat=$(ima $assFile) || exit 1
  rm -f $assFile


  attendu=$(cat $resultFile)

  echo "    Test for" $fich

  if [ "$resultat" = "$attendu" ]; then
      echo "    The result corresponds to the expected result"
  else
      echo "    The result doesn't correspond to the expected result. Indeed, the result is:"
      echo $resultat
  fi

done



