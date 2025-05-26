<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PDF Files Search</title>
    <style>
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 20px;
        }

        .search-container {
            display: flex;
            gap: 10px;
            margin-bottom: 30px;
        }

        #searchInput {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        #searchButton {
            padding: 10px 30px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .cards-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }

        .card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            cursor: pointer;
            transition: box-shadow 0.3s;
        }

        .card:hover {
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .card-id {
            color: #666;
            font-size: 0.9em;
            margin-bottom: 5px;
        }

        .card-name {
            font-weight: bold;
            font-size: 1.1em;
        }

        .empty-state {
            text-align: center;
            color: #666;
            padding: 40px;
        }

        #uploadButton {
                padding: 10px 30px;
                background: #2196F3; /* Синий цвет */
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background 0.3s;
            }

            #uploadButton:hover {
                background: #1976D2; /* Темнее синий при наведении */
            }

    </style>
</head>
<body>
    <div class="container">
        <div class="search-container">
            <input type="text" id="searchInput" placeholder="Search by document name...">
            <button id="searchButton">Search</button>
            <button
                    id="uploadButton"
                    onclick="window.location.href='${pageContext.request.contextPath}/front/upload'"
                >
                    Upload
                </button>
        </div>

        <div id="resultsContainer" class="cards-container"></div>
    </div>

    <script>
        // Initial load
        document.addEventListener('DOMContentLoaded', function() {
            performSearch('');
        });

        // Search button click handler
        document.getElementById('searchButton').addEventListener('click', function() {
            const searchTerm = document.getElementById('searchInput').value;
            performSearch(searchTerm);
        });

        // Enter key handler
        document.getElementById('searchInput').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                const searchTerm = this.value;
                performSearch(searchTerm);
            }
        });

        function performSearch(searchTerm) {
                    const params = new URLSearchParams();

                    if (searchTerm.trim() !== '') {
                        params.append('documentName', searchTerm);
                    }

                    console.log('${pageContext.request.contextPath}/api/pdfFiles?' + params.toString());

                    fetch('${pageContext.request.contextPath}/api/pdfFiles?' + params.toString())
                        .then(response => {
                            if (!response.ok) throw new Error('Network error');
                            return response.json();
                        })
                        .then(data => {
                            console.log(data);
                            updateResults(data);
                            })
                        .catch(error => {
                            console.error('Search error:', error);
                        });
                }

        function truncateText(text, maxLength = 15) {
            return text.length > maxLength
                ? text.substring(0, maxLength) + '...'
                : text;
        }



        function updateResults(files) {
                const container = document.getElementById('resultsContainer');

                if (!files || files.length === 0) {
                    container.innerHTML = '<div class="empty-state">No documents found</div>';
                    return;
                }


            container.innerHTML = files.map(file => `
                        <div class="card" onclick="window.location.href='${pageContext.request.contextPath}/front/fileDetails/\${file.documentId}'">
                            <div class="card-id">ID: \${file.documentId}</div>
                            <div class="card-name">NAME: \${truncateText(file.documentName)}</div>
                        </div>
                    `).join('');
        }
    </script>
</body>
</html>