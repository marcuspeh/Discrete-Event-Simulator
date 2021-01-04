# Part 1

### Compiling the codes
```javac Main.java```

### Generating full Java docs
```javadoc *.java -d docs -private```

### How to run simulation
To run the simulation, start off with```java Main```, followed by user input. The following is a sample output. User input is underlined, and starts with an integer value representing the number of servers, followed by the arrival times of the customers. Input is terminated with a ^D (CTRL-d).
```
1
0.500
0.600
0.700
^D
0.500 1 arrives
0.500 1 served by 1
0.600 2 arrives
0.600 2 waits to be served by 1
0.700 3 arrives
0.700 3 leaves
1.500 1 done serving by 1
1.500 2 served by 1
2.500 2 done serving by 1
[0.450 2 1]
```