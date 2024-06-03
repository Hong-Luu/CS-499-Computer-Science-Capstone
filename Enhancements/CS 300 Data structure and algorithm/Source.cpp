// CS 300
// Hong Luu
// Project Two

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <unordered_map>
#include <sstream>
#include <algorithm>

using namespace std;

// Define a structure to represent course info
struct Course {

    string courseNumber, courseTitle;
    vector<string> prerequisites; // vector of strings representing the prerequisites for the course
};
const unsigned int DEFAULT_SIZE = 179; // set the default size of the hash table to 179 (a prime number)


class HashTable {
private:
    // Define structures to hold courses
    struct Node {
        Course course; // the course associated with this node
        unsigned int key; // the hash key for this node
        Node* next; // a pointer to the next node in the linked list

        // default constructor
        Node() {
            key = UINT_MAX;
            next = nullptr;
        }

        // initialize with a course
        Node(Course aCourse) : Node() {
            course = aCourse;
        }

        // initialize with a course and a key
        Node(Course aCourse, unsigned int aKey) : Node(aCourse) {
            key = aKey;
        }
    };
    vector<Node> nodes; // the vector of nodes that represents the hash table

    unsigned int tableSize = DEFAULT_SIZE; // the size of the hash table

    unsigned int hash(int key); // the hash function used to compute the hash key

public:
    HashTable(); // Default Constructor
    HashTable(unsigned int size); // Constructor with a specific size
    virtual ~HashTable(); // Destructor

    void Insert(Course course); // function to insert a course into the hash table
    void PrintCourseList(); // function to print the list of all courses in the hash table
    void PrintCourse(); // function to print details of a specific course

};

// Define Default Constructor
HashTable::HashTable() {
    // set the table size to default value
    tableSize = DEFAULT_SIZE;
    // initialize 'nodes' vector by resizing the tableSize to default table size
    nodes.resize(tableSize);
}

// Define Constructor
HashTable::HashTable(unsigned int size) {
    // set the table size to given value
    tableSize = size;

    // resize the nodes vector to the table size
    nodes.resize(tableSize);
}

// Destructor
HashTable::~HashTable() {
    // loop through all nodes in the hash table
    for (Node& node : nodes) {
        Node* current = node.next;

        // loop through all nodes in the linked list and delete each node
        while (current != nullptr) {
            Node* temp = current;
            current = current->next;
            delete temp;
        }
    }
    // after all nodes are deleted, clear the vector to ensure all memory is released
    nodes.clear();
}

// Define the hash function to calculate hash value
unsigned int HashTable::hash(int key) {
    // use the modulo hash to get the key value - course number in this case
    return key % tableSize;
}
// Define Insert function
void HashTable::Insert(Course course) {
    // calculate the key value for the course using the hash function
    unsigned int key = 0;
    // compute the key for the course based on its course number
    for (char c : course.courseNumber) {
        key = (key * 10) + (c - '0');
    }
    // hash the key to get an index in the hash table
    key = hash(key);

    // retrieve node at the hash key position
    Node* node = &(nodes.at(key));

    // traverse the linked list to check for duplicates
    while (node->key != UINT_MAX) {
        // check if the course already exists
        if (node->course.courseNumber == course.courseNumber) {
            cout << "Course " << course.courseNumber << " already exists." << endl;
            return;
        }
        node = node->next;
    }

    // if no node is present at the hash key position, insert the course at the node
    if (node->key == UINT_MAX) {
        // assign the given course to the node's course
        node->course = course;
        // set the node's key to the calculated key value
        node->key = key;
    }
    // if the node is already used, find the next open node in the linked list
    else {
        // traverse the linked list to find the next open node
        Node* current = node->next;
        // traverse the linked list until we find the end or an empty node
        while (current != nullptr && current->next != nullptr) {
            current = current->next;
        }
        // create new node with the course and key
        Node* newNode = new Node(course, key);
        // insert the new node at the end of the linked list
        if (current == nullptr) {
            node->next = newNode;
        }
        else {
            current->next = newNode;
        }
    }
}

// Define PrintCourseList function to print all courses in list
void HashTable::PrintCourseList() {
    cout << "Here is a sample schedule:\n\n";
    // iterate through all nodes in the hash table
    for (Node& node : nodes) {
        Node* current = &node; // Change this line
        // check if there is a course at this node
        if (current != nullptr && current->key != UINT_MAX) {
            // add all courses for this node to a vector
            vector<Course> courses;
            courses.push_back(current->course);
            current = current->next;
            while (current != nullptr) {
                courses.push_back(current->course);
                current = current->next;
            }
            // sort the courses in the vector
            for (int i = 0; i < courses.size() - 1; i++) {
                for (int j = 0; j < courses.size() - i - 1; j++) {
                    if (courses[j].courseTitle > courses[j + 1].courseTitle) {
                        Course temp = courses[j];
                        courses[j] = courses[j + 1];
                        courses[j + 1] = temp;
                    }
                }
            }
            // output the sorted courses
            for (Course& course : courses) {
                cout << course.courseNumber << ", " << course.courseTitle << endl;
            }
        }
        else {
            continue;
        }
    }
    cout << endl; // add an empty line
}

// Define PrintCourse function to print a specific course obtained from user
void HashTable::PrintCourse() {
    // get input from user
    cout << "What course do you want to know about? ";
    // create a Course object to store the input course information
    Course course;
    // read in the course number from the user
    cin >> course.courseNumber;
    // convert the course number to lowercase using a lambda function
    transform(course.courseNumber.begin(), course.courseNumber.end(), course.courseNumber.begin(), ::toupper);

    // calculate the key value for the course using the hash function
    unsigned int key = 0;
    // compute the hash key for the course based on its course number
    for (char c : course.courseNumber) {
        // convert each digit of the course number to an integer and add it to the key
        key = (key * 10) + (c - '0');
    }
    // use the hash function to map the key to an index in the hash table
    key = hash(key);

    // find the node at the calculated key position in the hash table
    Node* node = &(nodes.at(key));
    // traverse the linked list at the calculated key position to find the node containing the course
    while (node != nullptr && node->key != UINT_MAX && node->course.courseNumber != course.courseNumber) {
        // move to the next node in the linked list
        node = node->next;
    }

    // if the node is empty or the course is not found
    if (node->key == UINT_MAX) {
        cout << "Course not found." << endl;
        return;
    }
    // output course title and number
    cout << node->course.courseNumber << ", " << node->course.courseTitle << endl;
    // if the course has prerequisites, output them
    if (node->course.prerequisites.size() > 0) {
        // output prerequisites
        cout << "Prerequisites: ";
        // loop through all prerequisites except the last one
        for (int i = 0; i < node->course.prerequisites.size() - 1; i++) {
            // output the prerequisite followed by a comma
            cout << node->course.prerequisites[i] << ", ";
        }
        // output the last prerequisite without a comma and end the line
        cout << node->course.prerequisites[node->course.prerequisites.size() - 1] << endl;
    }
    cout << endl;
}

// Define function to read the file and add data to data structure
void readCoursesFromFile(HashTable* hashTable) {
    string fileName;
    // output message to ask user to enter file name
    cout << "What file name do you want to load? ";
    cin >> fileName; // obtain file name from user to load into data structure
    cout << endl;

    // open the file
    ifstream file(fileName);

    // check if the file can be opened
    if (!file.is_open()) {
        cout << "Unable to open file: " << fileName << endl;
        return;
    }

    // read data and add data read from file to data structure
    string line;
    while (getline(file, line)) {
        // create a string stream for the current line
        istringstream iss(line);

        // extract the course data from the line
        string courseNumber, courseTitle, prerequisite;
        getline(iss, courseNumber, ','); // read the course number up to the comma
        getline(iss, courseTitle, ','); // read the course title up to the comma
        vector<string> prerequisites;
        while (getline(iss, prerequisite, ',')) { // read all the prerequisites up to the comma
            prerequisites.push_back(prerequisite); // add each prerequisite to the vector
        }

        // create a new course object with the extracted data
        Course course;
        course.courseNumber = courseNumber;
        course.courseTitle = courseTitle;
        course.prerequisites = prerequisites;

        // insert the course into the hash table
        try {
            hashTable->Insert(course);
        }
        // if the course number is invalid, catch the exception and handle it
        catch (std::invalid_argument& e) {
            // print an error message
            cout << "Invalid course number: " << courseNumber << " (skipping line)" << endl;
        }
    }
    file.close(); // close the file
}

int main() {
    // create a hash table object
    HashTable hashTable;

    // display the menu and get user input
    int option = 0;
    cout << "Welcome to the course planner.\n\n";
    while (option != 9) {
        // display menu options
        cout << "1. Load Data Structure." << endl;
        cout << "2. Print Course List." << endl;
        cout << "3. Print Course." << endl;
        cout << "4. Exit.\n" << endl;

        cout << "What would you like to do? ";
        cin >> option; // obtain option input from user

        // handle user input
        switch (option) {
        case 1:
            // load data structure
            readCoursesFromFile(&hashTable);
            break;
        case 2:
            // print course list
            hashTable.PrintCourseList();
            break;
        case 3:
            // print course obtained from user
            hashTable.PrintCourse();
            break;
        case 4:
            // exit the program
            cout << "Thank you for using the course planner!" << endl;
            exit(0); // exit the program
            break;
        default: // default message
            // invalid input
            cout << option << " is not a valid option.\n" << endl;
            break;
        }
    }
    return 0;
}