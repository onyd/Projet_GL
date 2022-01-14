#! /bin/sh

# Test de l'interface en ligne de commande de decac.
# On ne met ici qu'un test trivial, a vous d'en ecrire de meilleurs.

PATH=./src/main/bin:"$PATH"

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


decac_moins_n=$(decac -b)

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
