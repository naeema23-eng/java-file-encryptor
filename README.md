# 🔐 File Encryptor CLI (Java)

A simple and secure command-line tool to encrypt and decrypt files using AES encryption, written in Java.
Includes Docker support and CI testing with GitHub Actions.

---

## 🚀 Features

- Encrypt and decrypt files securely
- Password-based encryption (PBKDF2 + AES)
- Simple command-line interface
- Build with Maven and run as a standalone JAR
- Docker support for isolated execution
- GitHub Actions workflow for CI

---

## 🛠️ Build & Run Locally

### 🔧 Clone the repository:

```bash
git clone https://github.com/naeema23-eng/java-file-encryptor.git
cd java-file-encryptor
```

### 🔧 Build the project with Maven:

```bash
mvn clean package -DskipTests
```

The JAR file will be generated in the `target/` folder.

---

## 🔑 Usage

### 🔐 Encrypt a file:

```bash
java -jar target/file-encryptor.jar --encrypt -i data/secret.txt -o data/secret.txt.enc -p yourpassword
```

### 🔐 Decrypt a file:

```bash
java -jar target/file-encryptor.jar --decrypt -i data/secret.txt.enc -o data/secret.txt -p yourpassword
```

The encrypted/decrypted files will be saved in the specified output file.

---

## 🐳 Run with Docker (Optional)

### 🔧 Build the Docker image:

```bash
docker build -t java-encryptor .
```

### 🔐 Run encryption:

```bash
docker run --rm -v "$PWD":/data java-encryptor --encrypt -i /data/secret.txt -o /data/secret.txt.enc -p yourpassword
```

### 🔐 Run decryption:

```bash
docker run --rm -v "$PWD":/data java-encryptor --decrypt -i /data/secret.txt.enc -o /data/secret.txt -p yourpassword
```

---

## 🧪 Run Tests

Run all unit tests:

```bash
mvn test
```

---

## 🔄 CI Pipeline

GitHub Actions workflow runs automatically on each push to run the tests.

---

## 📦 Release Artifacts

Download the latest JAR file from the [Releases](https://github.com/naeema23-eng/java-file-encryptor/releases) page.
