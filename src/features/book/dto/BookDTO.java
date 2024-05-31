package features.book.dto;

public class BookDTO {

    public String name;
    public String author;
    public String category;
    public String ISBN;

    public BookDTO(String name, String author, String category, String ISBN) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.ISBN = ISBN;
    }

    // ...
}