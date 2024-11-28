<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>檔案上傳與下載</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 600px;
            margin: auto;
        }
        form {
            margin-bottom: 20px;
        }
        input[type="text"], input[type="file"], button {
            display: block;
            margin: 10px 0;
            padding: 10px;
            width: 100%;
        }
        button {
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>檔案上傳與下載</h1>

        <!-- 上傳檔案表單 -->
    <form action="/files/upload" method="post" enctype="multipart/form-data">
        <label for="folder">輸入目錄名稱：</label>
        <input type="text" id="folder" name="folder" placeholder="請輸入目錄名稱" required>
        
        <label for="file">選擇檔案：</label>
        <input type="file" id="file" name="file" required>
        
        <button type="submit">上傳檔案</button>
    </form>

        <!-- 下載檔案表單 -->
        <form id="downloadForm" action="/files/download" method="get" target="_blank">
            <label for="downloadFolder">下載目錄名稱：</label>
            <input type="text" id="downloadFolder" name="folder" placeholder="請輸入目錄名稱" required>

            <label for="fileName">檔案名稱：</label>
            <input type="text" id="fileName" name="fileName" placeholder="請輸入檔案名稱" required>

            <button type="submit">下載檔案</button>
        </form>
    </div>
</body>
</html>
