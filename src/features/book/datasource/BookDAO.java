package features.book.datasource;

import features.book.dto.BookDTO;
import features.book.model.Book;
import infrastructure.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class BookDAO implements BookDatabase, BookSubscriber {
    private final List<BookListener> listeners;

    public BookDAO() {
        this(new ArrayList<>());
    }

    public BookDAO(List<BookListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void subscribe(BookListener bookListener) {
        listeners.add(bookListener);
    }

    private void notifyDataChanged() {
        for(BookListener listener : listeners) {
            listener.updateData();
        }
    }

    @Override
    public void insertBook(BookDTO bookDTO) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                var book = new Book(bookDTO);
                session.persist(book);
            });
            System.out.println("features.book.model.Book inserted successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    @Override
    public void updateBook(int bookId, BookDTO bookDTO) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                var book = session.get(Book.class, bookId);
                book.setName(bookDTO.name);
                book.setAuthor(bookDTO.author);
                book.setCategory(bookDTO.category);
                book.setISBN(bookDTO.ISBN);
                session.persist(book);
            });
            System.out.println("features.book.model.Book edited successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error editing book: " + e.getMessage());
        }
    }

    @Override
    public void removeBook(int bookId) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                Book book = session.get(Book.class, bookId);
                if (book != null) {
                    session.delete(book);
                    System.out.println("features.book.model.Book deleted successfully.");
                } else {
                    System.out.println("Book with ID " + bookId + " not found.");
                }
            });
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

    @Override
    public List<Book> getBooks(String searchTerm) {
        List<Book> result = new ArrayList<>();
        try {
            result = DatabaseManager.getDatabaseSessionFactory().fromTransaction(session -> {
                // Build the search query dynamically
                String query = "from Book b where b.name like :searchTerm or b.author like :searchTerm";
                return session.createQuery(query, Book.class)
                        .setParameter("searchTerm", "%" + searchTerm + "%")
                        .getResultList();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void updateBookRented(int bookId, boolean rented) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                var book = session.get(Book.class, bookId);
                book.setRented(rented);
                session.persist(book);
            });
            System.out.println("features.book.model.Book edited successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error editing book: " + e.getMessage());
        }
    }
}

