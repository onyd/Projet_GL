#! /bin/sh

# Auteur : gl28
# Version initiale : 01/01/2022

# Test minimaliste de la vérification contextuelle.
# Le principe et les limitations sont les mêmes que pour basic-synt.sh
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

total=0
succ=0
interSucc=0
interTotal=0

test_context_valide () {
    if test_context "$1">standardOutput 2>&1
    then
        echo "Succès attendu pour test_context sur $1."
        succ=$((succ+1))
        interSucc=$((interSucc+1))
    else
        echo "Echec inattendu de test_context sur $1."
        #exit 1
    fi
}

test_context_invalide () {
    if test_context "$1">standardOutput 2>&1
    then
        echo "Succès inattendu pour test_context sur $1."
        #exit 1
    else
        echo "Echec attendu de test_context sur $1."
        succ=$((succ+1))
        interSucc=$((interSucc+1))
    fi
}

echo "Test invalides fournis :"
for cas_de_test in src/test/deca/context/invalid/provided/*.deca
do
    test_context_invalide "$cas _de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides fournis :"
for cas_de_test in src/test/deca/context/valid/provided/*.deca
do
    test_context_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest invalides personnalisés :"
for cas_de_test in src/test/deca/context/invalid/custom/*.deca
do
    test_context_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest invalides personnalisés sur les objects :"
for cas_de_test in src/test/deca/context/invalid/custom/object/*.deca
do
    test_context_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest invalides personnalisés sur les options :"
for cas_de_test in src/test/deca/context/invalid/custom/option/asm/*.deca
do
    test_context_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides personnalisés de fonctions de bases :"
for cas_de_test in src/test/deca/context/valid/custom/builtin_function/*.deca
do
    test_context_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides personnalisés de déclarations :"
for cas_de_test in src/test/deca/context/valid/custom/Declaration/*.deca
do
    test_context_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides personnalisés d'opérations binaires :"
for cas_de_test in src/test/deca/context/valid/custom/operation-binaires/*.deca
do
    test_context_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides personnalisés d'opérations unaires :"
for cas_de_test in src/test/deca/context/valid/custom/operation-unaire/*.deca
do
    test_context_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur la condition if :"
for cas_de_test in src/test/deca/context/valid/custom/if/*.deca
do
    test_context_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur les objects :"
for cas_de_test in src/test/deca/context/valid/custom/object/*.deca
do
    test_context_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur les options :"
for cas_de_test in src/test/deca/context/valid/custom/option/asm/*.deca
do
    test_context_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

echo "Le taux de reussite sur l'ensemble de la partie context est" $succ/$total