# CS 300 Data Structures and Algorithms: Vector Sorting Project

## Overview
As part of the CS 300 Data Structures and Algorithms course, this project delves into the practical applications and performance analysis of sorting algorithms. Using a dataset from municipal government auctions, this project implements and compares the efficiency of Selection Sort and QuickSort algorithms.

This documentation covers the entire project lifecycle, from the initial setup and implementation to the detailed performance analysis, reflecting on the insights gained and challenges faced during the development process.

## Project Goals
The primary objectives of this project are:
- **Implement Sorting Algorithms**: To code and understand two fundamental sorting algorithms—Selection Sort and QuickSort.
- **Analyze Performance**: To evaluate and compare the performance of these algorithms when sorting large datasets.
- **Enhance Problem-Solving Skills**: To apply theoretical knowledge in a practical scenario, enhancing problem-solving and coding skills.

## Users and Assumptions
This project assumes the user has basic understanding of:
- **Data Structures**: Knowledge of vectors and their operations.
- **Sorting Algorithms**: Understanding of basic sorting mechanisms and their computational complexities.

## Key Features
- **Load Bids from CSV**: Ability to parse and load data from CSV files into vectors.
- **Display All Bids**: Users can view all bids currently loaded into the application.
- **Selection Sort**: Implementation of the selection sort algorithm to order bids.
- **QuickSort**: Implementation of the quicksort algorithm for efficient sorting.
- **Performance Metrics**: Capture and display timing and computational metrics for each sort.

## Screens and Navigation
- **Main Menu**: Allows users to choose which operation to perform, such as loading data, displaying bids, or sorting the data using one of the implemented methods.

## Technical Implementation
- **Programming Language**: C++
- **Development Environment**: Developed using C++11 standards to leverage modern language features.
- **Algorithms Implemented**:
  - `void selectionSort(vector<Bid>& bids)`
  - `void quickSort(vector<Bid>& bids, int begin, int end)`
  - `int partition(vector<Bid>& bids, int begin, int end)`

## Enhancements and Reflections
### Enhancements
- **Optimization of Sorting Algorithms**: Improved the efficiency of both sorting algorithms through code refinement and optimization techniques.

### Reflections
- **Learning Outcomes**: Gained a deeper understanding of algorithm efficiency and data structures.
- **Challenges Encountered**: Addressed challenges related to data handling and algorithm optimization, especially in dealing with large datasets.

## Performance Comparison
- **Selection Sort**: Showed slower performance on larger datasets, taking significantly more time to sort.
- **QuickSort**: Demonstrated superior performance with much faster sorting times, even on large datasets.

## Screenshots
