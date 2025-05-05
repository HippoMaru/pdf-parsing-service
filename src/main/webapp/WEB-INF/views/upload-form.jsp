<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create PDF Details</title>
    <style>
        .container {
            max-width: 600px;
            margin: 30px auto;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input, textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        #message {
            margin-top: 15px;
            padding: 10px;
            display: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create New PDF File Details</h2>
        <form id="pdfForm">
            <div class="form-group">
                <label for="documentName">Document Name:</label>
                <input type="text" id="documentName" name="documentName" required>
            </div>

            <div class="form-group">
                <label for="author">Author:</label>
                <input type="text" id="author" name="author">
            </div>

            <div class="form-group">
                <label for="keywords">Keywords (comma-separated):</label>
                <input type="text" id="keywords" name="keywords">
            </div>

            <div class="form-group">
                <label for="recognizedText">Recognized Text:</label>
                <textarea id="recognizedText" name="recognizedText" rows="5"></textarea>
            </div>

            <button type="submit">Submit</button>
        </form>
        <div id="message"></div>
    </div>

    <script>
        document.getElementById('pdfForm').addEventListener('submit', function(e) {
            e.preventDefault();

            const formData = {
                documentName: document.getElementById('documentName').value,
                author: document.getElementById('author').value,
                keywords: document.getElementById('keywords').value,
                recognizedText: document.getElementById('recognizedText').value
            };

            fetch('${pageContext.request.contextPath}/api/pdfFiles', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
            .then(response => {
                if (response.ok) {
                    showMessage('Record created successfully!', 'green');
                    document.getElementById('pdfForm').reset();
                } else {
                    response.json().then(data => showMessage('Error: ' + data.message, 'red'));
                }
            })
            .catch(error => {
                showMessage('Error: ' + error.message, 'red');
            });
        });

        function showMessage(text, color) {
            const messageDiv = document.getElementById('message');
            messageDiv.style.display = 'block';
            messageDiv.style.color = color;
            messageDiv.textContent = text;
            setTimeout(() => messageDiv.style.display = 'none', 3000);
        }
    </script>
</body>
</html>