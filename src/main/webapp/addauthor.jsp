<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add a New Author</title>

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
            width: 90%;
            max-width: 600px;
            text-align: center;
        }  h2 {
            margin-bottom: 20px;
        }  form {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            margin: 0 auto;
            width: 100%;
            max-width: 400px;
        }  label {
            margin-bottom: 5px;
            font-weight: bold;
        }  input[type="text"] {
            padding: 8px;
            margin-bottom: 15px;
            width: 100%;
            border: 1px solid #ccc;
            border-radius: 4px;
        }  input[type="submit"] {
            align-self: center;
            padding: 10px 20px;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            color: #fff;
            cursor: pointer;
            font-size: 1rem;
        }  input[type="submit"]:hover {
            background-color: #0056b3;
        }  .back-link {
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
    <h2>Add a New Author</h2>
    <form action="LibraryData" method="post">
        <input type="hidden" name="type" value="author">

        <label for="author">Author Name:</label>
        <input type="text" id="author" name="author" required>

        <input type="submit" value="Add Author">
    </form>
    <div class="back-link">
        <a href="index.jsp">Back to Home</a>
    </div>
</div>
</body>
</html>



