package boulderTest;

import boulderTest.com.bd.ia.Joueur;
import boulderTest.com.bd.ia.Simplet;

public class Main {

    public static void main(String[] args) {
        String fichierNiveau = "assets/FireFox50.bd.bdcff";
        String fichierChemin = "";
        final boolean journal=true;
        int choixNiveau = -1;
        if (args.length != 0) {
            switch (args.length) {
                case 1:
                    switch (args[0]) {
                        case "-name":
                            System.out.println("BEGOUEN DEMEAUX Marine\nFABRE Marin\nSENAT Clement\nDE SA Thomas");
                            System.exit(0);
                            break;
                        case "-h":
                            System.out.println(usage());
                            break;
                        default:

                            System.exit(2);
                    }
                    break;
                case 2:
                    switch (args[0]) {
                        case "-lis":
                            fichierNiveau = args[1];
                            try {
                                EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                                System.out.println(lesNiveaux.toString());
                            } catch (Exception e) {
                                System.out.println("Erreur de lecture du fichier");
                            }
                            System.exit(0);
                            break;
                        case "-joue":
                            fichierNiveau = args[1];
                            break;
                        default:
                            System.out.println(usage());
                            System.exit(2);
                    }
                    break;
                case 3:
                    switch (args[0]) {
                        case "-joue":
                            fichierNiveau = args[1];
                            choixNiveau = Integer.parseInt(args[2]);
                            break;
                        case "-rejoue":
                            fichierNiveau = args[2];
                            try {
                                fichierChemin = args[1];
                                EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                                new Partie(lesNiveaux.choisirNiveau(choixNiveau), new FichierDash(fichierChemin),journal);
                            } catch (Exception e) {
                                System.out.println("Erreur de lecture du fichier .dash");
                                System.exit(2);
                            }
                            break;
                        default:
                            System.out.println(usage());
                            System.exit(2);
                            break;
                    }
                    break;
                case 4:
                    switch (args[0]) {
                        case "-rejoue":
                            fichierNiveau = args[2];
                            fichierChemin = args[1];
                            try {
                                choixNiveau = Integer.parseInt(args[3]);
                                //TO COMPLETE
                            } catch (Exception e) {
                                System.out.println("Erreur de lecture du fichier .dash");
                                System.exit(2);
                            }
                            break;
                        default:
                            System.out.println(usage());
                            System.exit(2);
                            break;
                    }
                    break;
                case 6:
                    // -rejoue
                    break;
                case 7:
                    // -simul
                    break;
                default:
                    System.out.println(usage());
                    System.exit(2);
            }
        } else {
            System.out.println("Bonjour !");
            try {
                EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                System.out.println(lesNiveaux.toString());

                new Partie(lesNiveaux.choisirNiveau(choixNiveau),new Joueur(journal),journal); //TEST

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static String usage() {
        return "Usage : $ java -jar boulder_dash.jar [-name][-h][-lis fichier.bdcff][-joue fichier.bdcff -niveau N]\n" +
                "[-cal strategie fichier.bdcff -niveau N][-rejoue fichier.dash fichier.bdcff -niveau N]\n" +
                "[-simul N strategie strategie fichier.bdcff -niveau N]\n" +
                "Voir PDF pour plus dinfos sur les commandes";
    }



}
