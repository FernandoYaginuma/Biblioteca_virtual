package features.book.model;

import features.book.dto.BookDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String author;
    private String category;
    private String ISBN;
    private boolean rented = false;

    public Book() {}

    public Book(BookDTO bookDTO) {
        this.name = bookDTO.name;
        this.author = bookDTO.author;
        this.category = bookDTO.category;
        this.ISBN = bookDTO.ISBN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor(){
        return  this.author;
    }
    public String getCategory(){
        return  this.category;
    }
    public String getISBN(){
        return  this.ISBN;
    }

    public void edit(String name, String author, String category, String ISBN){

    }

    public void setDescription(String description) {
//        this.description = description;
    }

    public boolean isRented() {
        return this.rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }
}
