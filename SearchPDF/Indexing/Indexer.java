/**
*  Program by: Sawsan Daban
*  
*  Indexing pdf files
*  Add index files in the index directory
*
*/

package Indexing;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class Indexer {
    IndexWriter writer;

    public Indexer(String indexDir) throws IOException {
		// open the index directory to add index files
        if(writer == null) {
        writer = new IndexWriter(FSDirectory.open(new File(indexDir)), new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)));
        }
    }
    
    public IndexItem index(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);
        String content = new PDFTextStripper().getText(doc);
        String path = file.getPath();
        doc.close();
        return new IndexItem((long)file.getName().hashCode(), file.getName(), content,path);
    }

    /** 
      * This method will add the items into index
      */
    public void addToIndex(IndexItem indexItem) throws IOException {

        // deleting the item, if already exists
        writer.deleteDocuments(new Term(IndexItem.ID, indexItem.getId().toString()));

        Document doc = new Document();

        doc.add(new Field(IndexItem.ID, indexItem.getId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field(IndexItem.TITLE, indexItem.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field(IndexItem.CONTENT, indexItem.getContent(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field(IndexItem.PATH, indexItem.getpath(), Field.Store.YES, Field.Index.NOT_ANALYZED));

        // add the document to the index
        writer.addDocument(doc);
    }
    
    /** 
      * This method will create the index
      */
    public int createIndex(String dataDirPath, FileFilter filter) throws IOException {
      File[] files = new File(dataDirPath).listFiles();
      IndexItem pdfIndexItem;

      for (File file : files) {
         if(!file.isDirectory()
            && !file.isHidden()
            && file.exists()
            && file.canRead()
            && filter.accept(file))
         {
            pdfIndexItem = index(file);
            addToIndex(pdfIndexItem);
         }
      }
      return writer.numDocs();
   }

    /**
      * Closing the index
      */
    public void close() throws IOException {
        writer.close();
    }
}
