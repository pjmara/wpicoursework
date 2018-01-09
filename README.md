# WPI Course Work

This contains all of the object oriented design projects completed in the course CS 210X at Worcester Polytechnic Institute.
All code was worked on by Paul Mara and Kepei Lei.

Project 1: Facebuk- A spoof of facebook to add friends. People or pets can have accounts, and they share a set of moments together. Various algorithms are in place such as finding the largest clique of friends possible (brute force) and finding sorting moments by "happiness level".

Project 2: LRU Cache- A (mostly theoretical) cache that can find data within a given data provider and store it in a cache to improve memory access speeds. Cache has a fixed size and will provide faster access to the data stored within it through a cache hit. This project was mostly for the idea of a cache.

Project 3: IMDB- We were required to write a script that parsed the IMDB data (stored in a .list file) for actors/actresses who acted in all of the movies on IMDB. After this data was parsed, it had to be inserted into a graph of either movies or actors. This graph was used to find the shortest connection between actors or movies through shared actors or movies.

Project 4: Mystery Data Structures- To observe properties of data structures, we were given a sample of data structures that we had to test. We had to write code to output data that we could analyze- specifically the time costs for get, set, and remove methods in best, worst, and average cases.

Project 5: Expression Editior- Given to us was a prebuilt window with a text field and a mouse handler to detect mouse clicks and drags. When the user typed a valid mathematical expression containing the symbols +, •, or (). The reason these symbols were the only allowed was because the nature of the project was to parse the expression into an editable tree, where the user can change the ordering. First, the user can click on different areas of their expression and a focus will appear if a deeper level in the expression tree can still be reached. Then, the user can drag around the expression to anywhere in the window, and a ghosted copy of what they are dragging will appear in the place that the program believes they are trying to drag it to. Our program calculates this by calculating all of the possible positions that the dragged expression could end up and places the ghosted expression in the one that most closely matches the users mouse position. 
