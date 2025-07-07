🔐 File Encryptor CLI (Java)

A simple and secure command-line tool to encrypt and decrypt files using AES encryption, written in Java.
Includes optional Docker support and CI testing with GitHub Actions.

🕹️ Features

Encrypt and decrypt files securely
Password-based encryption (PBKDF2 + AES)
Simple command-line interface with clear logs
Build with Maven and run as a standalone JAR
Unit tests with GitHub Actions CI
Docker support for isolated runs

🛠️ Build & Run Locally

🔧 Clone the repository:

git clone https://github.com/naeema23-eng/java-file-encryptor.git

cd java-file-encryptor

🔧 Build the project with Maven:

mvn clean package -DskipTests

The JAR file will be generated in the target/ folder.

🔧 Usage

🔐 Encrypt a file:

java -jar target/file-encryptor.jar -e -i data/secret.txt -p yourpassword

🔒 Decrypt a file:

java -jar target/file-encryptor.jar -d -i data/secret.txt.enc -p yourpassword

The encrypted/decrypted files will be saved in the same folder by default.

🐳 Run with Docker (Optional)

🔧 Build the Docker image:

docker build -t java-encryptor .

🔐 Run encryption:

docker run --rm -v "$PWD":/data java-encryptor -e -i /data/secret.txt -p yourpassword

🔒 Run decryption:

docker run --rm -v "$PWD":/data java-encryptor -d -i /data/secret.txt.enc -p yourpassword

🔧 Run Tests

mvn test

🔢 CI Pipeline

GitHub Actions workflow runs automatically on each push to run the tests.


