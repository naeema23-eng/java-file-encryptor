package com.example;

import org.apache.commons.cli.*;

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileEncryptorCLI {

    private static final Logger logger = Logger.getLogger(FileEncryptorCLI.class.getName());

    public static void main(String[] args) {
        Options options = new Options();

        options.addOption("e", "encrypt", false, "Encrypt the file");
        options.addOption("d", "decrypt", false, "Decrypt the file");
        options.addOption("i", "input", true, "Input file path");
        options.addOption("o", "output", true, "Output file path");
        options.addOption("p", "password", true, "Password");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);

            String inputFile = cmd.getOptionValue("input");
            String outputFile = cmd.getOptionValue("output");
            String password = cmd.getOptionValue("password");

            if (cmd.hasOption("encrypt")) {
                FileEncryptor.encryptFile(password, Paths.get(inputFile), Paths.get(outputFile));
                logger.info("Encryption completed: " + outputFile);
            } else if (cmd.hasOption("decrypt")) {
                FileEncryptor.decryptFile(password, Paths.get(inputFile), Paths.get(outputFile));
                logger.info("Decryption completed: " + outputFile);
            } else {
                formatter.printHelp("file-encryptor", options);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error: " + e.getMessage(), e);
            formatter.printHelp("file-encryptor", options);
        }
    }
}
