import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
//import java.util.random.*;

public class App {
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        String[] legitGuess = new String[12972];
        String[] words = new String[2315];
        try {
            File g = new File("guess.txt");
            File a = new File("allowed.txt");
            Scanner scan = new Scanner(a);


            for(int i = 0; scan.hasNextLine(); i++){
            words[i] = scan.nextLine();
            }
            scan.close();
            scan = new Scanner (g);
            for(int i = 0; scan.hasNextLine(); i++){
                legitGuess[i] = scan.nextLine();
            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(((System.nanoTime() - startTime) / 1000000000.0) + "s");
        startTime = System.nanoTime();
        Random rand = new Random();
        String randWord = words[rand.nextInt(2316)];
        System.out.println(randWord);
        char[][] response = new char[2][5];
        int avg = 0;
        int best = 0;
        int index = 0;
        for(int i = 0; i < legitGuess.length; i++){

            for(int a = 1; i < 4; i++){
                for(int b = 1; b < 4; b++){
                    for(int c = 1; c < 4; c++){
                        for(int d = 1; d < 4; d++){
                            for(int f = 1; f < 4; f++){
                                response[0] = legitGuess[i].toCharArray();
                                response[1][0] = (char) a;
                                response[1][1] = (char) b;
                                response[1][2] = (char) c; // not pretty
                                response[1][3] = (char) d;
                                response[1][4] = (char) f;
                                avg += nbrBadWords(response, words);
                            }
                        }
                    }
                }
            }
            avg = avg / 243;
            if(avg > best){
                index = i;
                best = avg;
            }
        }
        System.out.println(legitGuess[index]);
        System.out.println(((System.nanoTime() - startTime) / 1000000000.0) + "s");
        response = wordCheck(randWord, legitGuess[index]);
        words = remainingWords(response, words);

    }

    private static int nbrBadWords(char[][] resp, String[] word){
        // one for remebering all green chars
        // one for remebering all blue chars
        // one for remebering all gray
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Character> gray = new ArrayList<>();
        HashMap<Integer, Character> blue = new HashMap<Integer, Character>();
        HashMap<Integer, Character> green = new HashMap<>();
        words.addAll(words);
        for(int i = 0; i < resp.length; i++){
            switch (resp[i][1]) {
                case (char) 1:
                    gray.add(resp[i][0]);
                    break;
                case (char) 2:
                    blue.put(i, resp[i][0]); 
                    break;
                case (char) 3:
                    green.put(i, resp[i][0]);
                    break;
            }
        }
        for(int i = 0; i < gray.size(); i++){
            if(green.containsValue(gray.get(i))){
                gray.remove(i);
            }
        }
        for(int i = 0; i < gray.size(); i++){
            char k = gray.get(i);
            words.removeIf( (s1) -> (s1.matches(k + "*")));
        }
        for (int i : blue.keySet()) {
            char k = blue.get(i);
            words.removeIf( (s1) -> ((s1.matches(k + "*")) && (s1.charAt(i) != k)));
        }
        for (int i : green.keySet()) {
            char k = green.get(i);
            words.removeIf( (s1) -> (s1.charAt(i) == k));
        }
        

        return words.size() == 0 ? word.length : words.size() ;
    }

    private static String[] remainingWords(char[][] resp, String[] word){
        // one for remebering all green chars
        // one for remebering all blue chars
        // one for remebering all gray
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Character> gray = new ArrayList<>();
        HashMap<Integer, Character> blue = new HashMap<Integer, Character>();
        HashMap<Integer, Character> green = new HashMap<>();
        words.addAll(words);
        for(int i = 0; i < resp.length; i++){
            switch (resp[i][1]) {
                case (char) 1:
                    gray.add(resp[i][0]);
                    break;
                case (char) 2:
                    blue.put(i, resp[i][0]); 
                    break;
                case (char) 3:
                    green.put(i, resp[i][0]);
                    break;
            }
        }
        for(int i = 0; i < gray.size(); i++){
            if(green.containsValue(gray.get(i))){
                gray.remove(i);
            }
        }
        for(int i = 0; i < gray.size(); i++){
            char k = gray.get(i);
            words.removeIf( (s1) -> (s1.matches(k + "*")));
        }
        for (int i : blue.keySet()) {
            char k = blue.get(i);
            words.removeIf( (s1) -> ((s1.matches(k + "*")) && (s1.charAt(i) != k)));
        }
        for (int i : green.keySet()) {
            char k = green.get(i);
            words.removeIf( (s1) -> (s1.charAt(i) == k));
        }
        

        return (String[]) words.toArray(); // this does not work fucking java
    }

    private static char[][] wordCheck(String Correctword, String s2){
        
        
        char[][] temp = new char[2][5];
        return temp;
    }
}
