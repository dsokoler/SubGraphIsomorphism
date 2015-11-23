# SubGraphIsomorphism
CS355 Zero Knowlege Subgraph Isomorphism - Daniel Sokoler, Lala Vaishno De

How to run?
1. Pull the repo.
 . If you already have the .java files, generate random graphs using the graph functions
2. Open two terminals
3. In terminal 1, "javac Server.java" then "java Server #"
4. In terminal 2, "javac Client.java" then "java Client #"
Note: # is the number of rounds that should be run between the client and server

How to run directly as Eclipse project?
1. Make a new java project in Eclipse called SubGraphIsomorphism with the directory: /SubGraphIsomorphism/  (this is where you 
  have cloned the git repo)
2. All the files will be imported into the Eclipse project.


Notes : when you do isomorphism, make a new object that using the same adjacency matrix and call doIsomorphism using that. Else the original gets messed up (No idea why)
