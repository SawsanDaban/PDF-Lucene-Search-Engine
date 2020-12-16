/**
*  Program by: Sawsan Daban
*  
*  Create initial variables and setter and getter
*
*/

package Indexing;

public class IndexItem {
	
    private Long id;
    private String title;
    private String content;
    private String path;

    public static final String ID = "id"; //It is for storing the file name hash-code 
    public static final String TITLE = "title"; //for file name
    public static final String CONTENT = "content"; //for file content
    public static final String PATH = "path"; //for file path

    public IndexItem(Long id, String title, String content, String path) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    
    public String getpath() {
        return path;
    }

    @Override
    public String toString() {
        return "IndexItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

}
