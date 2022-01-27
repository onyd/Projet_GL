public class Main
{
    public static void main(String[]args)
    {
        // Utilisation d'une classe deca simple en java.
        NotreMath notreMath = new NotreMath();
        System.out.println("Somme entiere : " + notreMath.additionner(3,2));
        System.out.println("Soustraction entière : " + notreMath.substraire(8,1));
        System.out.println("Multiplication à virgule flottante : " + notreMath.multiplier(12.5f,4.1f));
        System.out.println("Division à virgule flottante : " + notreMath.diviser(25.0f,2.5f));

        int a = 12;
        int b = 5;

        System.out.println("La valeur minimale entre a et b : " + notreMath.minimumInt(a,b));

        System.out.println("----------------------------");

        //Limitation : on est obligé de copier le code du programme principal deca.
        CompteBancaire2 cb = new CompteBancaire2();
        cb.afficherSolde();
        cb.ajouterSolde(5);
        cb.afficherSolde();
    }
}

