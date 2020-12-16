/**
*  Program by: Sawsan Daban
*  
*  Search from the index files in the index directory
*
*/

package Searching;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.index.CorruptIndexException;

public class Searcher {
    private IndexSearcher searcher;
    private QueryParser contentQueryParser;
    private Query query;

    public Searcher(String indexDir) throws IOException {
        // open the index directory to search
        searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(indexDir))));
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

        // defining the query parser to search items by content field.
        contentQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.CONTENT, analyzer);
    }

    /**
      * This method is used to find the indexed items by the content.
      * @param queryString - the query string to search for
      */
    public TopDocs search(String queryString, int numOfResults) throws IOException, java.text.ParseException, org.apache.lucene.queryparser.classic.ParseException, ParseException {
      query = contentQueryParser.parse(queryString);
      return searcher.search(query, numOfResults);
    }
    
    public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
      return searcher.doc(scoreDoc.doc);  
    }

    public void close() throws IOException {
        searcher.close();
    }
}