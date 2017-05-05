// Name:     Isaiah Weaver for NASA

import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.*;
import java.lang.*;




public class Main {


    public static void main(String[] args) throws Exception {

        // Create a set of strings

        List<String> diseases = new ArrayList<String>();
        List<String> locations = new ArrayList<String>();
        String locationsPath = "/Users/johndoe/IdeaProjects/nasaDiseaseScrapper/src/locations.txt";
        String diseasesPath = "/Users/johndoe/IdeaProjects/nasaDiseaseScrapper/src/diseases.txt";

        uploadTextFiles(locations, locationsPath, diseases, diseasesPath);


        // Get command line arguments (in this case, it's only 1 arg that comes in as a string)
        String myUrl = args[0];

        // Read URL into a String
        String initialHTML = getHTML(myUrl); //getHTML is a ftn I created down below

        // Print statements
        System.out.println("This worked: " + myUrl);
        System.out.println("diseases: " + diseases);
        System.out.println("locations: " + locations);


        //Search initialHTML for the words in "diseases" list and "locations" list
        //parseHTML is a ftn I created down below
        //parseHTML uses isContainExactWord, a ftn I made below, which still recognizes a word regardless of capitalization or punctuation (check the regex in the pattern matching)
        parseHTML(initialHTML, diseases, locations);



    }

    //Upload both text files as lists (However, this splits up words based on spaces rather than next lines)
    public static void uploadTextFiles (List<String> locations, String locationspath, List<String> diseases, String diseasespath) throws FileNotFoundException{
        Scanner s = new Scanner(new File(diseasespath));
        while (s.hasNext()){
            diseases.add(s.next());
        }
        s.close();

        Scanner f = new Scanner(new File(locationspath));
        while (f.hasNext()){
            locations.add(f.next());
        }
        f.close();

    }

    public static void parseHTML (String initialHTML, List<String> diseases, List<String> locations){

        // Check location set
        if (isContainExactWord(initialHTML, locations)) {
            System.out.println("Location found.");
        } else {
            System.out.println("Location not found.");
        }
        // Check disease set
        if (isContainExactWord(initialHTML, diseases)) {
            System.out.println("Disease found.");
        } else {
            System.out.println("Disease not found.");
        }

    }

    // For this function, I return booleans, but you can also return the word (return s. But then change the function return type to public static String)
    public static boolean isContainExactWord(String initialHTML, List<String> wordSet){
        //Take the string HTML and make it a set of WORDS (split based on the reg ex below, which contains mostly punctuation)
        java.util.List<String> HTMLasList = java.util.Arrays.asList(initialHTML.split(" |,|\\."));

        //For each word in the HTML list of words...
        for(String s : HTMLasList)
        {
            // Asking if the sentence exists in the keywords (will work if the word is all lowercase, all uppercase, or exactly as entered in original List)
            Boolean foundAsIs = wordSet.contains(s);
            Boolean foundAsLower = wordSet.contains(s.toLowerCase());
            Boolean foundAsUpper = wordSet.contains(s.toUpperCase());
            if(foundAsIs || foundAsLower || foundAsUpper) {
                System.out.println("Found: " + s);
                return true;
            }
        }

        return false;

    }


    // This visits a website, pulls its HTML file, and returns the HTML in the form of a string
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
            //System.out.println(line);
        }
        rd.close();

        return result.toString();
    }

}
