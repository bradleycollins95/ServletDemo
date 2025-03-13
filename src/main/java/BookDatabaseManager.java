import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages database operations for books and authors in a MySQL database.
 * <p>
 * This class handles adding, updating, and retrieving data from two tables:
 * <strong>books</strong> and <strong>authors</strong>. It uses JDBC to connect
 * to the database, execute SQL statements, and handle results. Make sure the
 * database structure matches the SQL statements in this class (i.e., column
 * names, table names, etc.).
 * </p>
 *
 * <p><strong>Usage example:</strong></p>
 * <pre>
 * BookDatabaseManager manager = new BookDatabaseManager();
 * Book newBook = new Book("111-1-11-111111-1", "New Title", 1, "2025");
 * boolean added = manager.addBook(newBook);
 * </pre>
 */
public class BookDatabaseManager {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/books?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; //ENTER YOUR PASSWORD!

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found!");
        }
    }

    /**
     * Inserts a new book into the <strong>books</strong> table.
     *
     * @param book the {@link Book} object to be added.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean addBook(Book book) {
        String sql = "INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setInt(3, book.getEditionNumber());
            stmt.setString(4, book.getCopyright());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing book in the <strong>books</strong> table.
     * This method looks up the book to update by ISBN.
     *
     * @param book the {@link Book} object containing updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, editionNumber = ?, copyright = ? WHERE isbn = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getEditionNumber());
            stmt.setString(3, book.getCopyright());
            stmt.setString(4, book.getIsbn());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Inserts a new author into the <strong>authors</strong> table.
     * <p>
     * If successful, the generated authorID is retrieved from the database
     * and set in the {@link Author} object.
     * </p>
     *
     * @param author the {@link Author} object to be added.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean addAuthor(Author author) {
        String sql = "INSERT INTO authors (firstName, lastName) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    author.setAuthorID(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing author in the <strong>authors</strong> table.
     * This method looks up the author to update by <code>authorID</code>.
     *
     * @param author the {@link Author} object containing updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateAuthor(Author author) {
        String sql = "UPDATE authors SET firstName = ?, lastName = ? WHERE authorID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setInt(3, author.getAuthorID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a list of all {@link Book} objects from the <strong>titles</strong> table.
     * <p>
     * Note that the SQL references the <strong>titles</strong> table, which may differ
     * from the <strong>books</strong> table used elsewhere. Ensure your database schema
     * matches these queries.
     * </p>
     *
     * @return a {@link List} of all books; if none found, an empty list is returned.
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT isbn, title, editionNumber, copyright FROM titles"; //check if the table name is correct

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getInt("editionNumber"),
                        rs.getString("copyright")
                );

                books.add(book);
            }

            System.out.println("Fetched books: " + books); //debugger

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Retrieves a single {@link Book} object from the <strong>books</strong> table
     * based on the provided ISBN.
     *
     * @param isbn the ISBN of the book to retrieve.
     * @return the matching {@link Book} if found, or null otherwise.
     */
    public Book getBookByISBN(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getInt("editionNumber"),
                        rs.getString("copyright")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a single {@link Author} object from the <strong>authors</strong> table
     * based on the provided author ID.
     *
     * @param authorID the ID of the author to retrieve.
     * @return the matching {@link Author} if found, or null otherwise.
     */
    public Author getAuthorByID(int authorID) {
        String sql = "SELECT * FROM authors WHERE authorID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, authorID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Author(
                        rs.getInt("authorID"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all {@link Author} objects from the <strong>authors</strong> table.
     *
     * @return a {@link List} of all authors; if none found, an empty list is returned.
     */
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT authorID, firstName, lastName FROM authors"; //ensure table name is correct

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Author author = new Author(
                        rs.getInt("authorID"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );

                authors.add(author);
            }

            System.out.println("Fetched authors: " + authors); //debugger

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
