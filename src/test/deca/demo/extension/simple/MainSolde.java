public class MainSolde
{
    public static void main(String[]args)
    {
        //Limitation : on est obligé de copier le code du programme principal deca.

        CompteBancaire2 cb = new CompteBancaire2();
        cb.afficherSolde();
        cb.ajouterSolde(500);
        cb.afficherSolde();
        cb.soustraireSolde(100);
        cb.afficherSolde();
    }
}

