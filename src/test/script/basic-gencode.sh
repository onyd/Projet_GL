#! /bin/sh

# Auteur : gl28
# Version initiale : 01/01/2022

# Encore un test simpliste. On compile un fichier (cond0.deca), on
# lance ima dessus, et on compare le résultat avec la valeur attendue.

# Ce genre d'approche est bien sûr généralisable, en conservant le
# résultat attendu dans un fichier pour chaque fichier source.
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"

# On ne teste qu'un fichier. Avec une boucle for appropriée, on
# pourrait faire bien mieux ...
rm -f ./src/test/deca/codegen/valid/provided/ecrit0.ass 2>/dev/null
decac ./src/test/deca/codegen/valid/provided/ecrit0.deca || exit 1
if [ ! -f ./src/test/deca/codegen/valid/provided/ecrit0.ass ]; then
    echo "Fichier ecrit0.ass non généré."
    exit 1
fi

resultat=$(ima ./src/test/deca/codegen/valid/provided/ecrit0.ass) || exit 1
rm -f ./src/test/deca/codegen/valid/provided/ecrit0.ass

# On code en dur la valeur attendue.
attendu='ok\nok'
echo "${#attendu}"
echo "$attendu"

if [ "$resultat" = "$attendu" ]; then
    echo "Tout va bien"
else
    echo "Résultat inattendu de ima:"
    echo `expr substr $resultat 5`
    exit 1
fi
