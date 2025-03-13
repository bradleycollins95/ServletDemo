<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.LinkedHashSet" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
  <title>Author List</title>

  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
    }  .container {
      width: 90%;
      max-width: 600px;
      background: #fff;
      padding: 40px;
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
    }  .back-link {
      text-align: center;
      margin-top: 20px;
    }  .back-link a {
      color: #007bff;
      text-decoration: none;
      font-weight: bold;
    }  .back-link a:hover {
      text-decoration: underline;
    }
  </style>

</head>
<body>
<div class="container">
  <h2>Author List</h2>
  <%
    List<String> authors = (List<String>) request.getAttribute("authors");
    if (authors != null && !authors.isEmpty()) {
      // remove duplicate authors
      Set<String> authorSet = new LinkedHashSet<>(authors);
      // convert set back to a list
      authors = new ArrayList<>(authorSet);
      // sort case-insensitively
      Collections.sort(authors, String.CASE_INSENSITIVE_ORDER);
  %>
  <ul>
    <%
      for (String author : authors) {
    %>
    <li><%= author %></li>
    <%
      }
    %>
  </ul>
  <%
  } else {
  %>
  <p>No authors found.</p>
  <%
    }
  %>
  <div class="back-link">
    <a href="index.jsp">Back to Home</a>
  </div>
</div>
</body>
</html>





