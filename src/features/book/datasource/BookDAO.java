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
    public List<Book> getBooks() {
        List<Book> result = new ArrayList<>();
        try {
            result = DatabaseManager.getDatabaseSessionFactory().fromTransaction(session -> {
                return session.createSelectionQuery("from Book", Book.class)
                        .getResultList();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void markBookAsDone(int bookId) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                var book = session.get(Book.class, bookId);
                book.setRented(true);
                session.persist(book);
            });
            System.out.println("features.book.model.Book edited successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error editing book: " + e.getMessage());
        }
    }
}
