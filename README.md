# Playfair_Cipher
The Playfair cipher is a cryptographic technique that encrypts pairs of letters (digraphs) in plaintext to produce ciphertext. It was invented by Charles Wheatstone in 1854 but was popularized by Lyon Playfair during the Crimean War. The Playfair cipher encrypts pairs of letters at a time, making it significantly more secure than simple substitution ciphers.

# Overview
This Java program implements the a slightly modified Playfair cipher for encrypting and decrypting text messages. Instead of using the traditional 5 by 5 grid for encryption, this version uses a 6 by 6 grid containing alphabets as well as numbers. This was done so as to avoid the issues of omitted letters (Check the wiki to know more about it). The program takes a plaintext message and a key as input, and then performs encryption or decryption based on user choice. It supports only letters, digits, and whitespaces as input characters. 

# Features
* Encryption: Encrypts plaintext messages using the Playfair cipher algorithm.
* Decryption: Decrypts ciphertext messages back to plaintext using the same algorithm.
* Key Input: Allows the user to specify a key, which influences the encryption and decryption process.
* Input Validation: Ensures that input messages contain only letters, digits, and whitespaces.

# Usage
* Compile the Java source code using a Java compiler.
* Run the compiled Java program.
* Follow the on-screen instructions to input the plaintext message, key, and choose between encryption or decryption.
* View the encrypted or decrypted message output.

# File Structure
Playfair.java: Contains the main program code implementing the Playfair cipher algorithm.
README.md: This README file providing an overview and usage instructions for the program.

# Dependencies
This program has no external dependencies and can be run on any system with a Java Runtime Environment (JRE) installed.

# License
This program is licensed under the MIT License. See the LICENSE file for details.
