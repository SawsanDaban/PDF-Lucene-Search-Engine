/**
*  Program by: Sawsan Daban
*  
*  Create index files in the index directory
*  Search from the index files in the index directory
*
*/

package Searching;

import org.apache.lucene.queryParser.ParseException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import wordsentence.sentence;


public class Searching {

    
    // location where the index will be stored.
    private static final String INDEX_DIR = "/*Index directory path*/";
    private static final String DATA_DIR = "/*PDF files directory*/";
    private static final int DEFAULT_RESULT_SIZE = 300;
    public static final String FILE_PATH = "filepath";

    private static Indexer indexer;
    private static Searcher searcher;
    
    private static void createIndex(String indexDir, String dataDir) throws IOException {
      indexer = new Indexer(indexDir);
      int numIndexed;
      long startTime = System.currentTimeMillis(); // To calculate time 
      numIndexed = indexer.createIndex(dataDir, new TextFileFilter()); // Creating index an calculating indexed
      long endTime = System.currentTimeMillis(); // Calculates time when indexed finished
      indexer.close();
      System.out.println(numIndexed+" File indexed, time taken: "+(endTime-startTime)+" ms");    
    }
    
    public static void search(String indexDir, String dataDir, String searchQuery) throws IOException, java.text.ParseException, org.apache.lucene.queryparser.classic.ParseException, ParseException {

      searcher = new Searcher(indexDir);
      
      long startTime = System.currentTimeMillis(); // To calculate time 
      TopDocs hits = searcher.search(searchQuery,DEFAULT_RESULT_SIZE); // Getting the search results
      long endTime = System.currentTimeMillis(); // Calculates time when searching finished
   
      System.out.println("Total Results :"+hits.totalHits +" documents found. Time taken:" + (endTime - startTime)+" ms");
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File Title: "+ doc.get(IndexItem.TITLE)+ "\nFile Path: "+ doc.get(IndexItem.PATH);
      } 
    }
    
    
    
    public static void main(String[] args) throws IOException, ParseException {

        try {
            createIndex(INDEX_DIR, DATA_DIR); //Indexing files so we can search from them
            
            Scanner in = new Scanner(System.in);
            // creating an instance of the Searcher class to the query the index
            System.out.println("Enter what you want to search for: ");
            String s = in.nextLine();
            search(INDEX_DIR,DATA_DIR,s);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(Searching.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.apache.lucene.queryparser.classic.ParseException ex) {
            Logger.getLogger(Searching.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
