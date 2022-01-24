#! /bin/sh

# Test de l'interface en ligne de commande de decac.
# On ne met ici qu'un test trivial, a vous d'en ecrire de meilleurs.

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"

decac_moins_b=$(decac -b)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -b a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_b" = "" ]; then
    echo "ERREUR: decac -b n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_b" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -b contient erreur ou error"
    exit 1
fi

echo "Pas de probleme detecte avec decac -b."


decac_moins_p=$(decac -p)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -p a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_p" = "" ]; then
    echo "ERREUR: decac -p n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_p" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -p contient erreur ou error"
    exit 1
fi

echo "Pas de probleme detecte avec decac -p."

decac_moins_v=$(decac -v)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -v a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_v" = "" ]; then
    echo "ERREUR: decac -v n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_v" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -v contient erreur ou error"
    exit 1
fi

echo "Pas de probleme detecte avec decac -v."

#Lets test the -p instruction on two files
cd ~/Projet_GL/gl28/
decac -p src/test/deca/codegen/valid/custom/declaration/bool_declare.deca
decac -p src/test/deca/codegen/valid/custom/operation_affectation/float/plus.deca



decac_moins_P=$(decac -P)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -P a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_P" = "" ]; then
    echo "ERREUR: decac -P n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_P" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -P contient erreur ou error"
    exit 1
fi

echo "Pas de probleme detecte avec decac -P."

decac -P src/test/deca/codegen/valid/custom/declaration/bool_declare.deca src/test/deca/codegen/valid/custom/declaration/bool_op.deca

decac_moins_r=$(decac -r 4)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -r a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_r" = "" ]; then
    echo "ERREUR: decac -r n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_r" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -r contient erreur ou error"
    exit 1
fi

echo "Pas de probleme detecte avec decac -r."

decac -r 4 src/test/deca/codegen/perf/provided/ln2.deca