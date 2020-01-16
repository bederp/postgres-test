import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws SQLException {
        BookRepository repo = new BookRepository();

        Book book2 = new Book();

        book2.setBook_id(2L);
        book2.setTitle("someTitle");
        book2.setPage_sum(12);
        repo.addBook(book2);

        Optional<Book> book = repo.getBook(1);
        book.ifPresentOrElse(System.out::println, bookNotFoundAction());
    }

    private static Runnable bookNotFoundAction() {
        return () -> System.out.println("book not found");
    }
}
