#! /bin/sh

# Auteur : gl28
# Version initiale : 01/01/2022

# Test minimaliste de la syntaxe.
# On lance test_synt sur un fichier valide, et les tests invalides.

# dans le cas du fichier valide, on teste seulement qu'il n'y a pas eu
# d'erreur. Il faudrait tester que l'arbre donné est bien le bon. Par
# exemple, en stoquant la valeur attendue quelque part, et en
# utilisant la commande unix "diff".
#
# Il faudrait aussi lancer ces tests sur tous les fichiers deca
# automatiquement. Un exemple d'automatisation est donné avec une
# boucle for sur les tests invalides, il faut aller encore plus loin.

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

test_synt_valide () {
    if test_synt "$1">standardOutput 2>&1
    then
        echo "    Succès attendu pour test_synt sur $1."
        succ=$((succ+1))
        interSucc=$((interSucc+1))
    else
        echo "    Echec inattendu de test_synt sur $1."
        #exit 1
    fi
}

test_synt_invalide () {
    if test_synt "$1">standardOutput 2>&1
    then
        echo "    Succès inattendu pour test_synt sur $1."
        #exit 1
    else
        echo "    Echec attendu de test_synt sur $1."
        succ=$((succ+1))
        interSucc=$((interSucc+1))
    fi
}

total=0
succ=0
interSucc=0
interTotal=0

echo "${RED}This part is about parser tests${GREEN}"

echo "  Test invalides fournis :${NC}"
for cas_de_test in src/test/deca/syntax/invalid/provided/*.deca
do
    test_synt_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test valides fournis :${NC}"
for cas_de_test in src/test/deca/syntax/valid/provided/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test invalides personnalisés :${NC}"
for cas_de_test in src/test/deca/syntax/invalid/custom/*.deca
do
    test_synt_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\n  Test invalides personnalisés sur les objets :${NC}"
for cas_de_test in src/test/deca/syntax/invalid/custom/object/*.deca
do
    test_synt_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\n  Test invalides personnalisés sur les options :${NC}"
for cas_de_test in src/test/deca/syntax/invalid/custom/option/*.deca
do
    test_synt_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\n  Test valides personnalisés :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test valides personnalisés sur le print :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/print/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test valides personnalisés sur le println :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/println/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test valides personnalisés sur le printx :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/printx/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test valides personnalisés sur le printlnx :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/printlnx/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test valides personnalisés sur la condition if_then_else :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/if_then_else/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test valides personnalisés sur la condition while :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/while/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\n  Test valides personnalisés sur le return :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/return/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal

echo "\n  Test valides personnalisés sur les objets :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/object/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\n  Test valides personnalisés sur les options :${NC}"
for cas_de_test in src/test/deca/syntax/valid/custom/option/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "  ${GREEN}Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0
echo "${RED}Le taux de reussite sur l'ensemble de la partie syntax est"$succ/$total