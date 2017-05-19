package boulderTest;

import boulderTest.com.bd.ia.*;

public class Main {

    public static void main(String[] args) {
        String fichierNiveau = "assets/BD01plus.bdcff";
        String fichierChemin = "";
        int choixNiveau = -1;
        if (args.length != 0) {
            switch (args[0]){
                case "-name":
                    System.out.println("BEGOUEN DEMEAUX Marine\nFABRE Marin\nSENAT Clement\nDE SA Thomas");
                    System.exit(0);
                    break;
                case "-h":
                    System.out.println(usage());
                    break;
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
                    if(args.length == 4){
                        choixNiveau = Integer.parseInt(args[3]);
                        try {
                            EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                            Map laMap = lesNiveaux.choisirNiveau(choixNiveau);
                            new Partie(laMap, new Joueur(true),true); //TEST
                        } catch (Exception e) {
                            System.out.println(e.getClass());
                        }
                        System.exit(0);

                    }else {
                        try{
                            EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                            for(int i = 0 ; i < lesNiveaux.getSize() ; i++){
                                new Partie(lesNiveaux.choisirNiveau(i), new Joueur(true), true);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getClass());
                        }
                        System.exit(0);
                    }
                    break;
                case "-cal":
                    if(args.length >= 5){
                        int generations = 0;
                        int strEvolue = 0;
                        String strategie = args[1];
                        if (strategie.equals("-evolue") || strategie.equals("-direvol")) {
                            generations = Integer.parseInt(args[2]);
                            strEvolue = 1;
                        }
                        choixNiveau = Integer.parseInt(args[4 + strEvolue]);
                        try {
                            EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                            Map laMap = lesNiveaux.choisirNiveau(choixNiveau);

                            switch (strategie) {
                                case "-simplet":
                                    try {
                                        new Partie(laMap, new Simplet(), true); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                                case "-evolue":
                                    try {
                                        new Partie(laMap, new Evolue(generations, laMap, false, null), true); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                                case "-directif":
                                    try {
                                        new Partie(laMap, new Directif(), true); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                                case "-direvol":
                                    try {
                                        new Partie(laMap, new Direvol(generations, laMap), true); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                                case "-parfait":
                                    try {
                                        new Partie(laMap, new Parfait(laMap), true); //TEST
                                    } catch (Exception e) {
                                        System.out.println(e.getClass());
                                    }
                                    break;
                            }
                        }catch(Exception e2){
                            System.out.println(e2.getClass());
                        }
                        System.exit(0);
                    }
                    else usage();
                    break;
                case "-rejoue" :
                    if(args.length == 5){
                        fichierChemin = args[1];
                        fichierNiveau = args[2];
                        choixNiveau = Integer.parseInt(args[4]);
                        try {
                            EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                            Map laMap = lesNiveaux.choisirNiveau(choixNiveau);
                            new Partie(laMap,new FichierDash(fichierChemin),true);
                        }catch(Exception e2){
                            System.out.println(e2.getClass());
                        }
                    }
                    else usage();
                    break;
                case "-simul" :
                    if(args.length >= 7){
                        fichierNiveau = args[args.length-3];
                        choixNiveau = Integer.parseInt(args[args.length-1]);
                        int n = Integer.parseInt(args[1]);
                        String strategie1 = args[2];
                        int index2 = 3;
                        int mutation1 = 0;
                        if (strategie1.equals("-evolue") || strategie1.equals("-direvol")) {
                            mutation1 = Integer.parseInt(args[3]);
                            index2++;
                        }
                        int score = 0;
                        int time = 0;
                        Partie p;
                        try{
                            EnsembleNiveau lesNiveaux = new EnsembleNiveau(fichierNiveau);
                            Map laMap = lesNiveaux.choisirNiveau(choixNiveau);
                            for(int i = 0 ; i < n ; i++){
                                switch (strategie1){
                                    case "-evolue":
                                        p = new Partie(laMap.clone(),new Evolue(mutation1,laMap.clone(),false,null),false);
                                        break;
                                    case "-directif":
                                        p = new Partie(laMap.clone(),new Directif(),false);
                                        break;
                                    case "-direvol":
                                        p = new Partie(laMap.clone(),new Direvol(mutation1,laMap.clone()),false);
                                        break;
                                    case "-parfait":
                                        p = new Partie(laMap.clone(),new Parfait(laMap.clone()),false);
                                        break;
                                    case "-simplet":
                                        p = new Partie(laMap.clone(),new Simplet(),false);
                                        break;
                                    default:
                                        System.out.println(usage());
                                        System.exit(2);
                                }
                                score += p.getScore();
                                time += p.getTime();
                            }
                            score = score / n;
                            time = time / n;
                            System.out.println("Stratégie : "+strategie1+"\nScore moyen : "+score+" Temps moyen : "+time);

                            int bscore = 0;
                            int btime = 0;
                            String strategie2 = args[index2];
                            int mutation2 = 0;
                            if (strategie2.equals("-evolue") || strategie2.equals("-direvol")) {
                                mutation2 = Integer.parseInt(args[index2+1]);
                            }
                            for(int i = 0 ; i < n ; i++){
                                switch (strategie2){
                                    case "-evolue":
                                        p = new Partie(laMap.clone(),new Evolue(mutation1,laMap.clone(),false,null),false);
                                        break;
                                    case "-directif":
                                        p = new Partie(laMap.clone(),new Directif(),false);
                                        break;
                                    case "-direvol":
                                        p = new Partie(laMap.clone(),new Direvol(mutation1,laMap.clone()),false);
                                        break;
                                    case "-parfait":
                                        p = new Partie(laMap.clone(),new Parfait(laMap.clone()),false);
                                        break;
                                    case "-simplet":
                                        p = new Partie(laMap.clone(),new Simplet(),false);
                                        break;
                                    default:
                                        System.out.println(usage());
                                        System.exit(2);
                                }
                                bscore += p.getScore();
                                btime += p.getTime();
                            }
                            bscore = bscore / n;
                            btime = btime / n;
                            System.out.println("Stratégie : "+strategie1+" Score moyen : "+score+" Temps moyen : "+time);
                            System.out.println("Stratégie : "+strategie2+" Score moyen : "+bscore+" Temps moyen : "+btime);
                            System.exit(0);
                        }catch (Exception e){
                            System.out.println(e.getClass());
                        }


                    } else usage();
                    break;



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
