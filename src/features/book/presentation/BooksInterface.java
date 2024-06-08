package features.book.presentation;

public interface BooksInterface {
    void open(Boolean isAdmin, int userId, boolean isJustUserBooks);
    void showErrorMessage(String msg);
}
