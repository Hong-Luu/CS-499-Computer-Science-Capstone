# CS 300 Artifact: Academic Advising Application with Hash Table Implementation

## Overview
This artifact represents a significant enhancement to an academic advising application developed for the CS 300 course. The application leverages a hash table to efficiently manage course data, facilitating quick access and manipulation of course information crucial for academic advisors at ABCU.

## Enhancement Category: Algorithms and Data Structures

## Description
The application uses a hash table to store course information, including course numbers, titles, and prerequisites. This data structure was chosen for its efficiency in search, insert, and delete operations, which are vital in environments where quick data retrieval is essential.

## Objectives
- **Optimize Data Retrieval**: Use a hash table to enhance the speed and efficiency of data operations.
- **Improve Scalability and Performance**: Enable the application to handle a growing dataset without performance degradation.
- **Support Complex Data Interactions**: Facilitate advanced search capabilities and efficient data management.

## Key Functionalities
- **Hash Table for Data Management**: Utilizes a hash table where each entry contains a linked list to handle collisions, ensuring efficient data retrieval even under heavy load.
- **Dynamic Data Insertion and Retrieval**: Allows academic advisors to dynamically search for courses based on various attributes and insert new course data seamlessly.
- **Course Data Management**: Supports operations such as listing all courses and retrieving detailed information about specific courses.

## Code Review and Optimizations
The code is structured to provide a robust backend for the command-line interface. Key components include:

- **Hash Function**: The custom hash function uses the modulo operation, `key % tableSize`, to determine the index for each course based on its course number. This approach effectively reduces collisions and distributes data evenly across the hash table.

- **Collision Handling**: Collisions are managed using chaining with linked lists. Each node in the hash table can link to another node, allowing multiple courses to exist at the same index if they share the same hash key.

- **Insert Function**: The `Insert` function checks for duplicates before insertion, ensuring that each course number is unique within the table. If a collision occurs, the new course node is added to the end of the chain at the appropriate index.

- **Search and Print Functions**: The `PrintCourseList` function iterates through the hash table and prints each course in an organized manner. The `PrintCourse` function allows for searching a specific course by its number, demonstrating effective use of the hash table for fast data retrieval.

## Enhancements
- **Enhanced Search Efficiency**: The optimized hash function and collision handling mechanism significantly improve the speed of search operations.
- **User Interface Improvements**: The command-line interface is straightforward, offering clear prompts and handling user input effectively to interact with the hash table.

## Reflections
- **Challenges**: Implementing an effective collision handling mechanism posed challenges, particularly in maintaining efficient access times as the dataset grows.
- **Learning Outcomes**: This project enhanced my understanding of hash tables and their practical application in software development. I developed a deeper appreciation for the complexities of data structure choice and its impact on application performance.

## Conclusion
This artifact demonstrates my ability to implement and optimize complex data structures to improve the functionality and performance of software applications. It reflects my growth in software development and my readiness to tackle advanced data management challenges in real-world applications.
