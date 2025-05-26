<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Document Details</title>
    <style>
            /* Основной контейнер */
            .details-container {
                display: grid;
                grid-template-columns: 1fr 2fr;
                gap: 2rem;
                padding: 20px;
                align-items: start; /* Важно! */
            }

            /* Общие стили для обеих колонок */
            .metadata-column,
            .text-column {
                display: flex;
                flex-direction: column;
                gap: 1rem;
            }

            /* Заголовки */
            .metadata-column h2,
            .text-column h2 {
                margin: 0;
                padding: 0.5rem 0;
                border-bottom: 2px solid #ddd;
                font-size: 1.4rem;
                height: 40px; /* Фиксированная высота */
            }

            /* Левая колонка */
            .metadata-column {
                border-right: 1px solid #ccc;
                padding-right: 20px;
            }

            /* Правая колонка */
            .text-column {
                white-space: pre-wrap;
                font-family: monospace;
                max-height: 80vh;
                overflow-y: auto;
            }

            /* Детали метаданных */
            .detail-item {
                margin-bottom: 15px;
            }

            .detail-label {
                font-weight: bold;
                color: #666;
            }

            .back-button {
                        padding: 10px 20px;
                        background: #2196F3;
                        color: white;
                        text-decoration: none;
                        border-radius: 4px;
                        transition: background 0.3s;
                    }

            .back-button:hover {
                background: #1976D2;
            }
        </style>
</head>
<body>
    <a href="${pageContext.request.contextPath}/front/main"
           class="back-button"
           style="position: fixed; top: 20px; right: 20px;">
            ← Back to Main
    </a>

    <div class="details-container">
        <div class="metadata-column" id="metadata">
            <h2>Document Metadata</h2>
        </div>

        <div class="text-column" id="recognizedText">
            <h2>Recognized Text</h2>
        </div>
    </div>

    <script>
        // Получаем ID из URL
        const pathSegments = window.location.pathname.split('/');
        const docId = pathSegments[pathSegments.length - 1];

        // Делаем запрос к API
        fetch(`${pageContext.request.contextPath}/api/pdfFiles/\${docId}`)
            .then(response => {
                if (!response.ok) throw new Error('Document not found');
                return response.json();
            })
            .then(data => {
                renderMetadata(data);
                renderText(data.recognizedText);
            })
            .catch(error => {
                document.getElementById('metadata').innerHTML = `
                    <div class="error">Error: ${error.message}</div>
                `;
            });

        function renderMetadata(file) {
            const fields = {
                'Document ID': file.documentId,
                'Document Name': file.documentName,
                'Author': file.author,
                'Keywords': file.keywords,
                'Upload Date': new Date(file.uploadDate).toLocaleDateString()
            };

            document.getElementById('metadata').innerHTML = `
                <h2>Document Metadata</h2>
                \${Object.entries(fields).map(([label, value]) => `
                    <div class="detail-item">
                        <div class="detail-label">\${label}:</div>
                        <div>\${value || 'N/A'}</div>
                    </div>
                `).join('')}
            `;
        }

        function renderText(text) {
            document.getElementById('recognizedText').innerHTML = `
                <h2>Recognized Text</h2>
                <div class="text-content">\${text}</div>
            `;
        }
    </script>
</body>
</html>