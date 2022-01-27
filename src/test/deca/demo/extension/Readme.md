## Compilation pour la JVM

Compilation pour la Java virtual machine ou JVM
La machine virtuelle JVM a un rôle similaire au rôle de la machine virtuelle fournie IMA.
Ce rôle consiste à :
1- recevoir un fichier contenant le programme compilé
2- réaliser des vérifications techniques telles que vérifier la version du compilateur qui a compilé ce fichier.
3- préparer l'environnement de l’exécution par exemple créer le chemin d'exécution principal ou le thread.
4- exécuter les instructions du programme.

## Utilisation d’une classe deca compilée en bytecode dans un programme java.

Les étapes que je vais faire sont :
1- choisir un programme deca (CompteBancaire.deca)
2- compiler ce programme avec notre compilateur sans bytecode
3- exécuter le code compilé (le .ass) avec IMA.
4- exécuter le code compilé (le bytecode) avec la Java Virtual machine JVM.

5- compiler une classe deca (NotreMath.deca) puis l’utiliser en Main.java.
6- compiler une classe deca plus complexe (vehcules.deca) puis l’utiliser en testVehcules.java.
