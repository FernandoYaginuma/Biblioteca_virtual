public class Book {
    private final String imageUrl;
    private final String title;
    private final String author;

    public Book(String title, String imageUrl, String author) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
    }

    public String getImage(){
        return this.imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }
}
