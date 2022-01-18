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

test_synt_valide () {
    if test_synt "$1">fichier 2>&1
    then
        echo "Succès attendu pour test_synt sur $1."
        succ=$((succ+1))
        interSucc=$((interSucc+1))
    else
        echo "Echec inattendu de test_synt sur $1."
        #exit 1
    fi
}

test_synt_invalide () {
    if test_synt "$1">fichier 2>&1
    then
        echo "Succès inattendu pour test_synt sur $1."
        #exit 1
    else
        echo "Echec attendu de test_synt sur $1."
        succ=$((succ+1))
        interSucc=$((interSucc+1))
    fi
}

total=0
succ=0
interSucc=0
interTotal=0

echo "Test invalides fournis :"
for cas_de_test in src/test/deca/syntax/invalid/provided/*.deca
do
    test_synt_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest valides fournis :"
for cas_de_test in src/test/deca/syntax/valid/provided/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest invalides personnalisés :"
for cas_de_test in src/test/deca/syntax/invalid/custom/*.deca
do
    test_synt_invalide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal
interSucc=0
interTotal=0

echo "\nTest valides personnalisés :"
for cas_de_test in src/test/deca/syntax/valid/custom/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur le print :"
for cas_de_test in src/test/deca/syntax/valid/custom/print/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur le println :"
for cas_de_test in src/test/deca/syntax/valid/custom/println/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur le printx :"
for cas_de_test in src/test/deca/syntax/valid/custom/printx/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur le printlnx :"
for cas_de_test in src/test/deca/syntax/valid/custom/printlnx/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur la condition if_then_else :"
for cas_de_test in src/test/deca/syntax/valid/custom/if_then_else/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur la condition while :"
for cas_de_test in src/test/deca/syntax/valid/custom/while/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

interSucc=0
interTotal=0

echo "\nTest valides personnalisés sur le return :"
for cas_de_test in src/test/deca/syntax/valid/custom/return/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

echo "\nTest valides personnalisés sur les objets :"
for cas_de_test in src/test/deca/syntax/valid/custom/object/*.deca
do
    test_synt_valide "$cas_de_test"
    total=$((total+1))
    interTotal=$((interTotal+1))
done
echo "Le taux de reussite dans cette partie est" $interSucc/$interTotal

echo "Le taux de reussite sur l'ensemble de la partie syntax est" $succ/$total