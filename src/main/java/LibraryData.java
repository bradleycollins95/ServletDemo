import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * A servlet that handles adding books and viewing books/authors from a library.
 * <p>
 * Mapped to the <code>/LibraryData</code> path, this servlet processes:
 * <ul>
 *     <li>POST requests for adding new books (with minimal author info).</li>
 *     <li>GET requests for viewing all books or authors.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Example usage (POST):</strong></p>
 * <pre>
 *   POST /LibraryData
 *   Parameters:
 *       type = "book"
 *       title = "Effective Java"
 *       author = "Joshua Bloch"
 * </pre>
 *
 * <p><strong>Example usage (GET):</strong></p>
 * <pre>
 *   GET /LibraryData?view=books
 *   or
 *   GET /LibraryData?view=authors
 * </pre>
 */
@WebServlet("/LibraryData")
public class LibraryData extends HttpServlet {
    private BookDatabaseManager dbManager;

    /**
     * Initializes the servlet and instantiates a {@link BookDatabaseManager} to handle
     * database interactions for this servlet.
     */
    @Override
    public void init() {
        dbManager = new BookDatabaseManager();
    }

    /**
     * Handles HTTP POST requests for adding either a new book or a new author.
     * <p>
     * This method checks the <code>type</code> parameter in the form data:
     * <ul>
     *   <li><strong>type=book</strong>:
     *     <ul>
     *       <li>Expects the following parameters: <code>isbn</code>, <code>title</code>, <code>editionNumber</code>, <code>copyright</code>, and <code>author</code>.</li>
     *       <li>Validates the input (e.g., ensuring none are blank, parsing editionNumber).</li>
     *       <li>Constructs a {@link Book} and an {@link Author}, associates them, and calls {@code dbManager.addBook(book)} to insert into the database.</li>
     *       <li>Redirects to <em>index.jsp</em> with a success or failure message.</li>
     *     </ul>
     *   </li>
     *   <li><strong>type=author</strong>:
     *     <ul>
     *       <li>Expects the parameter <code>author</code>.</li>
     *       <li>Splits the author's name into first and last names if two names are provided.</li>
     *       <li>Constructs an {@link Author} and calls {@code dbManager.addAuthor(author)} to insert into the database.</li>
     *       <li>Redirects to <em>index.jsp</em> with a success or failure message.</li>
     *     </ul>
     *   </li>
     * </ul>
     * If either <code>type</code> or a required parameter is missing or invalid,
     * the user is redirected to an <em>error.jsp</em> page with an explanatory message.
     * </p>
     *
     * @param request  the {@link HttpServletRequest} containing form data
     * @param response the {@link HttpServletResponse} used for redirects or forwarding
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type"); //hidden field to distinguish between book & author

        if ("book".equals(type)) {
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            String editionStr = request.getParameter("editionNumber");
            String copyright = request.getParameter("copyright");
            String authorName = request.getParameter("author");

            if (isbn == null || isbn.trim().isEmpty() ||
                    title == null || title.trim().isEmpty() ||
                    editionStr == null || editionStr.trim().isEmpty() ||
                    authorName == null || authorName.trim().isEmpty() ||
                    copyright == null || copyright.trim().isEmpty())
            {
                response.sendRedirect("index.jsp?msg=Missing+one+or+more+required+fields");
                return;
            }

            int editionNumber;
            try {
                editionNumber = Integer.parseInt(editionStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("index.jsp?msg=Invalid+edition+number");
                return;
            }

            //split the author name into first/last
            String[] nameParts = authorName.split(" ", 2);
            String firstName = nameParts[0];
            String lastName = (nameParts.length > 1) ? nameParts[1] : "";

            Book book = new Book(isbn, title, editionNumber, copyright);
            Author author = new Author(0, firstName, lastName);
            book.addAuthor(author);

            //insert the author and book into the db
            boolean successfullyAddedBook = dbManager.addBook(book);
            boolean successfullyAddedAuthor = dbManager.addAuthor(author);


            if (successfullyAddedBook && successfullyAddedAuthor) {
                response.sendRedirect("index.jsp?msg=Book+and+author+added+successfully");
            } else {
                response.sendRedirect("index.jsp?msg=Database+insertion+failed");
            }

        } else if ("author".equals(type)) {
            String authorName = request.getParameter("author");

            if (authorName == null || authorName.trim().isEmpty()) {
                response.sendRedirect("index.jsp?msg=Missing+author+name");
                return;
            }

            //split into first/last names
            String[] nameParts = authorName.split(" ", 2);
            String firstName = nameParts[0];
            String lastName = (nameParts.length > 1) ? nameParts[1] : "";

            Author author = new Author(0, firstName, lastName);

            //insert into the DB
            boolean success = dbManager.addAuthor(author);

            if (success) {
                response.sendRedirect("index.jsp?msg=Author+added+successfully");
            } else {
                response.sendRedirect("index.jsp?msg=Database+insertion+failed");
            }
        }
    }



    /**
     * Handles HTTP GET requests to view all books or all authors.
     * <p>
     * Expects a <code>view</code> parameter:
     * <ul>
     *   <li><code>?view=books</code> - retrieves all books, setting the <code>books</code> attribute for forwarding to <code>viewbooks.jsp</code>.</li>
     *   <li><code>?view=authors</code> - retrieves all authors, setting the <code>authors</code> attribute for forwarding to <code>viewauthors.jsp</code>.</li>
     * </ul>
     * </p>
     *
     * @param request  the {@link HttpServletRequest} containing the query parameter
     * @param response the {@link HttpServletResponse} used for forwarding to JSP pages
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("books".equals(view)) {
            List<Book> books = dbManager.getAllBooks();
            List<String> bookStrings = new ArrayList<>();
            for (Book book : books) {
                bookStrings.add(book.getTitle() + " (ISBN: " + book.getIsbn() + ")");
            }
            request.setAttribute("books", bookStrings);
            request.getRequestDispatcher("viewbooks.jsp").forward(request, response);
        } else if ("authors".equals(view)) {
            List<Author> authors = dbManager.getAllAuthors();
            List<String> authorStrings = new ArrayList<>();
            for (Author author : authors) {
                authorStrings.add(author.getFirstName() + " " + author.getLastName());
            }
            request.setAttribute("authors", authorStrings);
            request.getRequestDispatcher("viewauthors.jsp").forward(request, response);
        }
    }

}
