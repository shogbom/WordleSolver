import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
//import java.util.random.*;

public class App {
    private static final char green = (char) 3;
    private static final char blue = (char) 2;
    private static final char gray = (char) 1;
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        String[] legitGuess = new String[12972];
        String[] words = new String[2315];
        try {
            File g = new File("wordlesolver/guess.txt");
            File a = new File("wordlesolver/allowed.txt");
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

        print(((System.nanoTime() - startTime) / 1000000000.0) + "s");
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
            if(avg < best){
                index = i;
                best = avg;
            }
        }
        print(legitGuess[index]);
        print(((System.nanoTime() - startTime) / 1000000000.0) + "s");
        response = wordCheck(randWord, legitGuess[index]);
        words = remainingWords(response, words);
        //legitGuess = remainingWords(response, legitGuess);
        print(charArrayToString(response));
        avg = 0;
        best = 0;
        index = 0;
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
                                response[1][3] = (char) d; // doesnt account for impossible permutations. :(
                                response[1][4] = (char) f; // but accounting for only possible permutations would be very slow.
                                avg += nbrBadWords(response, words);
                            }
                        }
                    }
                }
            }
            avg = avg / 243;
            if(avg < best){
                index = i;
                best = avg;
            }
        }
        print(legitGuess[index]);
        print(((System.nanoTime() - startTime) / 1000000000.0) + "s");
        response = wordCheck(randWord, legitGuess[index]);
        words = remainingWords(response, words);
        print(charArrayToString(response));
        //legitGuess = remainingWords(response, legitGuess);


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
            words.removeIf( (s1) -> (s1.charAt(i) != k));
        }
        
        
        return words.toArray(new String[words.size()]); // this is an insane hack, why the fuck is this the defualt way to do it??????
        //return (String[]) Word.toArray();    // this does not work fucking java
    }
    private static char[][] wordCheck(String correctword, String s2){
        char[][] temp = new char[2][5];
        ArrayList<Integer> alreadychecked = new ArrayList<>();
        for(int i = 0; i < 5; i++){ // SHOULD ADD FUNCTIONALITY FOR IF OCURS ONLY ONCE ONLY ONE GREEN SHOULD APEAR
            temp[0][i] = s2.charAt(i);
            if(s2.charAt(i) == correctword.charAt(i)){
                temp[1][i] = (char) 3; // grön
            } else {
                temp[1][i] = (char) 1; // grå
                for(int k = 0; k < 5; k++){

                    if(s2.charAt(i) == correctword.charAt(k)){
                        if(!alreadychecked.contains(k)){
                            temp[1][i] = (char) 2; // blå
                            alreadychecked.add(k); 
                        }
                    }
                }
            }

        }
        return temp;
    }
    private static void print(String f){
        System.out.println(f);
    }
    private static String charArrayToString(char[][] k){
        String printable = "";
        for(int x = 0; x < k[0].length ; x++){
            printable += k[0][x] + " ";
            switch (k[1][x]) {
                case green:
                    printable += "green \n";
                    break;
                case blue:
                    printable += "blue \n";
                    break;
                case gray:
                    printable += "gray \n";
                    break;
            }
        }
        return printable;
    }
}
