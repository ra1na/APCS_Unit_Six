import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SpellChecker {
    public static boolean wordInDictionary(String word, String[] dictionary) {
        int min = 0;
        int max = dictionary.length - 1;
        while (max >= min) {
            int mid = (min + max) / 2;
            if (dictionary[mid].equals(word)) {
                return true;
            }
            else if (dictionary[mid].compareTo(word) > 0) {
                max = mid - 1;
            }
            else {
                min = mid + 1;
            }
        }
        return false;
    }


    public static String[] getDictionary() throws IOException { // Collects the words inside the "words" text file and puts them into an array.
        String[] dictionary = new String[61336];
        Scanner wordsScan = new Scanner(new File("files/words.txt"));
        int count = 0;
        while (wordsScan.hasNext()) {
            dictionary[count] = wordsScan.next();
            count++;
        }
        return dictionary;
    }


    public static void main (String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Scanner textScan = new Scanner(new File("files/alice.txt"));
        FileWriter fw = new FileWriter("files/corrected.txt");
        String[] dictionary = getDictionary();
        String confirm = "";
        boolean answered = false;
        String newWord = "";
        String word = "";
        System.out.println("Checking the text file \"alice.txt\" for typos using words provided by \"words.txt\"...");
        while (textScan.hasNext()) {
            word = textScan.next();
            if (wordInDictionary(word, dictionary)) {
                fw.write(word + " ");
            }
            else {
                System.out.println("");
                System.out.println("The word \"" + word + "\" is not found within the provided dictionary.\nWould you like to rewrite the word or keep it as is?\nType \"Y\" to rewrite or \"N\" to keep.");
                confirm = scan.nextLine();
                while (!answered) {
                    if (confirm.equals("Y") || confirm.equals("y")) {
                        System.out.println("Please enter the rewritten word.");
                        newWord = scan.nextLine();
                        fw.write(newWord + " ");
                        System.out.println("The word \"" + word + "\" has been replaced with \"" + newWord + "\".");
                        answered = true;
                    }
                    else if (confirm.equals("N") || confirm.equals("n")) {
                        fw.write(word + " ");
                        System.out.println("The word \"" + word + "\" has not been replaced.");
                        answered = true;
                    }
                    else {
                        System.out.println("Please enter a valid answer.");
                    }
                }
                answered = false;
            }
        }
        fw.close();
        System.out.println("");
        System.out.println("The text file \"alice.txt\" has been checked for typos.\nThe corrected text can be found in the file \"corrected.txt\".");
    }
}
