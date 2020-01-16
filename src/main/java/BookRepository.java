import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2", "postgres", "postgres");
    }

    Optional<Book> getBook(long id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BOOKS WHERE book_id=?");

        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(mapToBookFrom(resultSet));
        }
        return Optional.empty();
    }

    private Book mapToBookFrom(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setBook_id(resultSet.getLong(1));
        book.setTitle(resultSet.getString(2));
        book.setAuthor(resultSet.getString(3));
        book.setPage_sum(resultSet.getInt(4));
        book.setYear_of_publication(resultSet.getInt(5));
        book.setPublishing_house(resultSet.getString(6));
        return book;
    }

    List<Book> getAllBooks() throws SQLException {
        Book book = new Book();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books;");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            books.add(mapToBookFrom(resultSet));
        }
        return books;

    }

    void addBook(Book book) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.books(\n" +
                "\tbook_id, title, author, page_sum, year_of_publication, publishing_house)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?);");

        setPreparedStatementWithBookParams(book, preparedStatement);
        preparedStatement.execute();

    }

    private void setPreparedStatementWithBookParams(Book book, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, book.getBook_id());
        preparedStatement.setString(2, book.getTitle());
        preparedStatement.setString(3, book.getAuthor());
        preparedStatement.setInt(4, book.getPage_sum());
        preparedStatement.setInt(5, book.getYear_of_publication());
        preparedStatement.setString(6, book.getPublishing_house());
    }
}
