public class Book {
    private final String title;
    private final String author;
    private final String category;
    private final String ISBN;
    public boolean status = true;

    public Book(String title, String imageUrl, String author, String category, String isbn) {
        this.category = category;
        this.title = title;
        this.author = author;
        ISBN = isbn;
    }


    public String getTitle() {
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }
}
