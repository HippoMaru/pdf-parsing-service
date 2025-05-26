<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload PDF File</title>
    <style>
        .container {
            max-width: 600px;
            margin: 30px auto;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .upload-area {
            border: 2px dashed #ddd;
            padding: 30px;
            text-align: center;
            margin-bottom: 20px;
            cursor: pointer;
        }
        .upload-area:hover {
            border-color: #4CAF50;
        }
        #fileInput {
            display: none;
        }
        #fileName {
            margin-top: 10px;
            color: #666;
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
        <h2>Upload PDF File</h2>
        <form id="uploadForm">
            <div class="upload-area" onclick="document.getElementById('fileInput').click()">
                <div>Click to select PDF file or drag and drop</div>
                <input type="file" id="fileInput" name="file" accept="application/pdf">
                <div id="fileName"></div>
            </div>
            <button type="submit">Upload PDF</button>
        </form>
        <div id="message"></div>
    </div>

    <script>
        const fileInput = document.getElementById('fileInput');
        const fileName = document.getElementById('fileName');

        // Handle file selection
        fileInput.addEventListener('change', function(e) {
            if (this.files.length > 0) {
                fileName.textContent = this.files[0].name;
            }
        });

        // Handle form submission
        document.getElementById('uploadForm').addEventListener('submit', function(e) {
            e.preventDefault();

            if (!fileInput.files.length) {
                showMessage('Please select a PDF file', 'red');
                return;
            }

            const formData = new FormData();
            formData.append('file', fileInput.files[0]);

            fetch('${pageContext.request.contextPath}/api/pdfFiles', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (response.ok) {
                    showMessage('File uploaded successfully!', 'green');
                    fileInput.value = '';
                    fileName.textContent = '';
                } else {
                    response.json().then(data => showMessage('Error: ' + data.message, 'red'));
                }
            })
            .catch(error => {
                showMessage('Error: ' + error.message, 'red');
            });
        });

        // Drag and drop handlers
        const uploadArea = document.querySelector('.upload-area');

        uploadArea.addEventListener('dragover', (e) => {
            e.preventDefault();
            uploadArea.style.borderColor = '#4CAF50';
        });

        uploadArea.addEventListener('dragleave', () => {
            uploadArea.style.borderColor = '#ddd';
        });

        uploadArea.addEventListener('drop', (e) => {
            e.preventDefault();
            uploadArea.style.borderColor = '#ddd';
            const files = e.dataTransfer.files;
            if (files.length > 0) {
                fileInput.files = files;
                fileName.textContent = files[0].name;
            }
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