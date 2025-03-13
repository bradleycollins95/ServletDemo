<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Library Management</title>

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
      background: #fff;
      padding: 40px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
      max-width: 600px;
      width: 90%;
      text-align: center;
    }  h1 {
      margin-bottom: 30px;
    }  .grid-container {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 20px;
    }  .grid-item {
      background-color: #eee;
      border-radius: 8px;
      padding: 20px;
      transition: background 0.2s ease;
    }  .grid-item a {
      color: #007bff;
      text-decoration: none;
      font-weight: bold;
    }  .grid-item:hover {
      background: #ddd;
    }
  </style>

</head>
<body>
<div class="container">
  <h1>Library</h1>
  <div class="grid-container">
    <div class="grid-item">
      <a href="addbook.jsp">Add a New Book</a>
    </div>
    <div class="grid-item">
      <a href="addauthor.jsp">Add a New Author</a>
    </div>
    <div class="grid-item">
      <a href="LibraryData?view=books">View Books</a>
    </div>
    <div class="grid-item">
      <a href="LibraryData?view=authors">View Authors</a>
    </div>
  </div>
</div>
</body>
</html>



