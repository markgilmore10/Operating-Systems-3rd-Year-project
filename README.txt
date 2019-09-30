README

Name: Mark Gilmore
Student Number: G00214777

.zip file contents:
oop.jar
src
README.txt
design.png
docs

Run program by typing java –cp ./oop.jar ie.gmit.sw.Runner in the command prompt.

This program was built using not only my own code but code provided in class from GMIT lecturer Dr. John Healy.
Online resourses were also used as outlined in CONSUMER below.

RUNNER:
Runner class includes main and sends user straight to the Menu class.

MENU:
Menu allows the user to enter the directory of the first file (dir) and the name of the file the user wishes to compare (doc).
The .txt extension must be included in the file name.
The user is then prompted for the second file which is from the local directory.
Again the .txt extension must be included.

Both files are then sent to the Processor.

A Do While loop controls the menu, giving the user the option of comparing more documents (y) or exiting (n);

PROCESSOR:
The processor then processes the documents and starts threads.

FILEPARSER:
The FileParser class is passes the text files, changes it all to lower case etc. and "puts" them in the queue.

CONSUMER:
The Consumer class takes shingles from the blocking queue and processes them.
Within this class the cosine similarity is also calculated. This part of the code was mostly taken from:
          https://blog.nishtahir.com/2015/09/19/fuzzy-string-matching-using-cosine-similarity/

WORD:
The Word class is attributes of a Word / Shingle.

POISON:
The Poison class is a special Word / Shingle which indicates that the final shingle has been taken from the file. 

