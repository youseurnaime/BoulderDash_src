package boulderTest;

import boulderTest.com.bd.ia.*;

public class Main {

    public static void main(String[] args) {
        String fichierNiveau = "assets/BD01plus.bdcff";
        String fichierChemin = "";
        boolean customOutput = false;
        final boolean journal = true;
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
                            // geré par le default de toute facon
                            break;
                        default:
                            System.out.println(usage());
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
                        default:
                            System.out.println(usage());
                            System.exit(2);
                    }
                    break;
                case 4:
                    if (args[0].equals("-joue") && args[2].equals("-niveau") && args[3].matches(".*\\d.*")) {
                        fichierNiveau = args[1];
                        choixNiveau = Integer.parseInt(args[3]);
                    } else {
                        System.out.println(usage());
                        System.exit(2);
                    }
                    // Lancer partie classique
                    System.exit(0);
                    break;
                case 5:
                    if (args[0].equals("-rejoue")) {
                        fichierChemin = args[1];
                        fichierNiveau = args[2];
                        if (args[3].equals("-niveau") && args[4].matches(".*\\d.*")) {
                            choixNiveau = Integer.parseInt(args[4]);
                        } else {
                            System.out.println(usage());
                            System.exit(2);
                        }
                        try {
                            customOutput = true;
                            EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                            new Partie(lesNiveaux.choisirNiveau(choixNiveau), new FichierDash(fichierChemin), journal, customOutput, fichierChemin);
                        } catch (Exception e) {
                            System.out.println("Erreur de lecture du fichier .dash");
                            System.exit(2);
                        }
                        System.exit(0);
                    } else {
                        System.out.println(usage());
                        System.exit(2);
                    }
                    break;
                case 7:
                    // -simul
                    // Lancer partie simul
                    break;
                default:
                    if (args[0].equals("-cal")) {
                        int generations = 0;
                        int strEvolue = 0;
                        String strategie = args[1];
                        if (strategie.equals("-evolue") || strategie.equals("-direvol")) {
                            generations = Integer.parseInt(args[2]);
                            strEvolue = 1;
                        }
                        fichierChemin = args[2 + strEvolue];
                        customOutput = true;
                        if (args[3 + strEvolue].equals("-niveau") && args[4 + strEvolue].matches(".*\\d.*")) {
                            choixNiveau = Integer.parseInt(args[4 + strEvolue]);
                        } else {
                            System.out.println(usage());
                            System.exit(2);
                        }
                        try {
                            EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                            System.out.println(lesNiveaux.toString());
                            Map laMap = lesNiveaux.choisirNiveau(choixNiveau);
                            System.out.println(laMap.ecranDeJeu());

                            switch (strategie) {
                                case "-simplet":
                                    // Lancer partie simplet
                                    break;
                                case "-evolue":
                                    try {
                                        new Partie(laMap, new Evolue(generations, laMap, false, null), true, customOutput, null); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                                case "-directif":
                                    try {
                                        new Partie(laMap, new Directif(), true, customOutput, null); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                                case "-direvol":
                                    try {
                                        new Partie(laMap, new Direvol(generations, laMap), true, customOutput, null); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                                case "-parfait":
                                    try {
                                        new Partie(laMap, new Parfait(laMap), true, customOutput, null); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                            }
                        }catch(Exception e2){
                            System.out.println(e2.getClass());
                        }
                    } else {
                        System.out.println(usage());
                        System.exit(2);
                    }
            }
        } else {
            System.out.println("No args :/");
            System.out.println(usage());
            System.exit(2);
        }
    }

    public static String usage() {
        return "Usage : $ java -jar boulder_dash.jar [-name][-h][-lis fichier.bdcff][-joue fichier.bdcff -niveau N]\n" +
                "[-cal strategie fichier.bdcff -niveau N][-rejoue fichier.dash fichier.bdcff -niveau N]\n" +
                "[-simul N strategie strategie fichier.bdcff -niveau N]\n" +
                "Voir PDF pour plus dinfos sur les commandes";
    }


}
