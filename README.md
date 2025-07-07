# File Encryptor CLI (Java)

A simple and secure command-line tool to encrypt and decrypt files using AES encryption, written in Java. Includes Docker support and CI testing with GitHub Actions.

## Features

- Encrypt and decrypt files securely
- Password-based encryption (PBKDF2 + AES)
- Simple command-line interface
- Build with Maven and run as a standalone JAR
- Docker support for isolated execution
- GitHub Actions workflow for CI

## Build

Clone the repository and build using Maven:

```bash
git clone https://github.com/yourusername/java-file-encryptor.git
cd java-file-encryptor
mvn clean package -DskipTests
