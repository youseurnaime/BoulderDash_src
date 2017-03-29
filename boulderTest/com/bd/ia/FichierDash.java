package boulderTest.com.bd.ia;

import boulderTest.FileFormatException;
import boulderTest.Map;
import boulderTest.com.bd.ia.Rockford;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by marin on 17/03/2017.
 */

public class FichierDash extends Rockford {

    private ConcurrentLinkedQueue<Character> lesDeplacements;
    private boolean attendre;

    public FichierDash (String fichier) throws FileFormatException {
        String strDeplacements = dashToString(fichier);
        System.out.println("Entrez \"o\" pour jouer immédiatement toute la partie.");
        System.out.println("Entrez une autre valeur et vous devrez taper une valeur à la fin de chaque tour pour passer au suivant.");
        String s;
        Scanner sc = new Scanner(System.in);
        do {
            s = sc.next();
        } while (s.equals(""));
        if (s.equals("o")) attendre = false;
        else attendre = true;
        lesDeplacements = new ConcurrentLinkedQueue<Character>();
        for(int i = 0 ; i < strDeplacements.length() ; i++){
            lesDeplacements.add(strDeplacements.charAt(i));
        }
    }

    public Point getDeplacement(Point posRockford, Map laMap) {
        Scanner sc = new Scanner(System.in);
        if(attendre) sc.nextLine();
        char c = lesDeplacements.remove();
        return charToPos(posRockford,c);
    }

    private static String dashToString(String fichier) throws FileFormatException {
        StringTokenizer str = new StringTokenizer(fichier, ".");//On vérifie qu'on a un .dash
        String extension = "";
        while (str.hasMoreElements()) {
            extension = str.nextToken();
        }
        if (!extension.equals("dash")) throw new FileFormatException();

        File fichierNiveau = new File(fichier);
        String ligne = "";
        String chemin = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichier));
            while ((ligne = br.readLine()) != null) {

                if (!ligne.contains("#")) {
                    chemin += ligne;
                }

            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture du fichier .dash");
        }

        return chemin;
    }
}
