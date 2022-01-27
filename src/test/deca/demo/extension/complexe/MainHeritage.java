public class MainHeritage
{
    public static void main(String[] args)
    {
        Ensimag e = new Ensimag();
        e.setNbEleve(1000);
        e.setBudget(3000000);

        if(e.estDansINP()) {
            System.out.println("Cette école est bien dans l'INP");
        }
        System.out.println("le nombre d'élèves à l'ensimag est de " + e.getNbEleve());
        System.out.println("le budget de l'ensimag est " + e.getBudget() + " euros");
    }
}
