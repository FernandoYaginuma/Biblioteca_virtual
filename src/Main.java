
import java.util.ArrayList;
import java.util.List;

public class Main {
    public ArrayList<Book> books = new ArrayList<>();
    public List<Usuario> usuarios = new ArrayList<>();
    private AddBookFrame addBookFrame = new AddBookFrame(this);
    private DashboardFrame dashboardFrame = new DashboardFrame(this);

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    private void init() {
        dashboardFrame.show(true);
    }

    public void openAddBookFrame(){
        addBookFrame.show(true);
    }

    private void updateDashboard(){
        dashboardFrame.frame.dispose();
        dashboardFrame = new DashboardFrame(this);
        dashboardFrame.show(true);
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Livro adicionado: " + book.getTitle());
        this.updateDashboard();
    }

    public void removeBook(Book book) {
        books.remove(book);
        this.updateDashboard();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
