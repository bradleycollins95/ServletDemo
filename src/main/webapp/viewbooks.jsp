<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Books</title>

    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }  .container {
            max-width: 800px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }  h2 {
            text-align: center;
            margin-bottom: 20px;
        }  ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }  li {
            padding: 10px 0;
            border-bottom: 1px solid #ccc;
        }  li:last-child {
            border-bottom: none;
        }  a {
            color: #007bff;
            text-decoration: none;
        }  a:hover {
            text-decoration: underline;
        }  .center-link {
            text-align: center;
            margin-top: 20px;
        }
    </style>

</head>
<body>
<div class="container">
    <h2>Book List</h2>
    <%
        // sorting
        List<String> books = (List<String>) request.getAttribute("books");
        if (books != null && !books.isEmpty()) {
            Collections.sort(books, String.CASE_INSENSITIVE_ORDER);
    %>
    <ul>
        <%
            for (String book : books) {
        %>
        <li><%= book %></li>
        <%
            }
        %>
    </ul>
    <%
    } else {
    %>
    <p>No books found.</p>
    <%
        }
    %>
    <div class="center-link">
        <a href="index.jsp">Back to Home</a>
    </div>
</div>
</body>
</html>


