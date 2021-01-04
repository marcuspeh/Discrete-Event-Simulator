# Part 2

### Compiling the codes
```javac Main.java```

### Generating full Java docs
```javadoc *.java -d docs -private```

### How to run simulation
There are several ways to run the simulation.

#### Way 1
Input to the program comprises (in order of presentation):
* an int value denoting the base seed for the RandomGenerator object;
* an int value representing the number of servers
* an int representing the number of customers (or the number of arrival * events) to simulate
* a positive double parameter for the arrival rate, λ
* a positive double parameter for the service rate, μ

The following is a sample run for way 1.
```
$ java Main 1 1 5 1.0 1.0
0.000 1 arrives
0.000 1 served by server 1
0.313 1 done serving by server 1
0.314 2 arrives
0.314 2 served by server 1
0.417 2 done serving by server 1
1.205 3 arrives
1.205 3 served by server 1
1.904 3 done serving by server 1
2.776 4 arrives
2.776 4 served by server 1
2.791 4 done serving by server 1
3.877 5 arrives
3.877 5 served by server 1
4.031 5 done serving by server 1
[0.000 5 0]
```

#### Way 2
Input to the program comprises (in order of presentation):
* an int value denoting the base seed for the RandomGenerator object;
* an int value representing the number of servers
* an int value for the maximum queue length, Qmax
* an int representing the number of customers (or the number of arrival events) to simulate
* a positive double parameter for the arrival rate, λ
* a positive double parameter for the service rate, μ

The following is a sample run for way 2.
```
$ java Main 1 2 2 10 1.0 1.0
0.000 1 arrives
0.000 1 served by server 1
0.313 1 done serving by server 1
0.314 2 arrives
0.314 2 served by server 1
0.417 2 done serving by server 1
1.205 3 arrives
1.205 3 served by server 1
1.904 3 done serving by server 1
2.776 4 arrives
2.776 4 served by server 1
2.791 4 done serving by server 1
3.877 5 arrives
3.877 5 served by server 1
3.910 6 arrives
3.910 6 served by server 2
3.922 6 done serving by server 2
4.031 5 done serving by server 1
9.006 7 arrives
9.006 7 served by server 1
9.043 8 arrives
9.043 8 served by server 2
9.105 9 arrives
9.105 9 waits to be served by server 1
9.160 10 arrives
9.160 10 waits to be served by server 1
10.484 7 done serving by server 1
10.484 9 served by server 1
10.781 9 done serving by server 1
10.781 10 served by server 1
10.833 10 done serving by server 1
11.636 8 done serving by server 2
[0.300 10 0]
```

#### Way 3
Input to the program comprises (in order of presentation):
* an int value denoting the base seed for the RandomGenerator object;
* an int value representing the number of servers
* an int value for the maximum queue length, Qmax
* an int representing the number of customers (or the number of arrival events) to simulate
* a positive double parameter for the arrival rate, λ
* a positive double parameter for the service rate, μ
* a positive double parameter for the resting rate, ρ
* a double parameter for the probability of resting, Pr

The following is a sample run for way 3.
```
$ java Main 1 2 2 20 1.0 1.0 0.1 0.5
0.000 1 arrives
0.000 1 served by server 1
0.313 1 done serving by server 1
0.314 2 arrives
0.314 2 served by server 1
0.417 2 done serving by server 1
1.205 3 arrives
1.205 3 served by server 2
1.904 3 done serving by server 2
2.776 4 arrives
2.776 4 served by server 2
2.791 4 done serving by server 2
3.877 5 arrives
3.877 5 served by server 1
3.910 6 arrives
3.910 6 served by server 2
3.922 6 done serving by server 2
4.031 5 done serving by server 1
9.006 7 arrives
9.006 7 served by server 1
9.043 8 arrives
9.043 8 served by server 2
9.105 9 arrives
9.105 9 waits to be served by server 1
9.160 10 arrives
9.160 10 waits to be served by server 1
9.225 11 arrives
9.225 11 waits to be served by server 2
10.148 12 arrives
10.148 12 waits to be served by server 2
10.484 7 done serving by server 1
10.484 9 served by server 1
10.781 9 done serving by server 1
11.205 13 arrives
11.205 13 waits to be served by server 1
11.636 8 done serving by server 2
11.636 11 served by server 2
11.688 11 done serving by server 2
11.688 12 served by server 2
12.429 14 arrives
12.429 14 waits to be served by server 2
13.109 15 arrives
13.109 15 waits to be served by server 2
14.644 10 served by server 1
15.013 10 done serving by server 1
15.178 12 done serving by server 2
15.178 14 served by server 2
15.264 16 arrives
15.264 16 waits to be served by server 1
15.338 14 done serving by server 2
15.338 15 served by server 2
15.524 17 arrives
15.524 17 waits to be served by server 2
15.940 18 arrives
15.940 18 waits to be served by server 2
17.793 19 arrives
17.793 19 leaves
18.207 15 done serving by server 2
18.207 17 served by server 2
18.765 20 arrives
18.765 20 waits to be served by server 2
19.103 17 done serving by server 2
19.103 18 served by server 2
20.146 18 done serving by server 2
40.474 13 served by server 1
40.480 13 done serving by server 1
40.480 16 served by server 1
41.056 16 done serving by server 1
57.110 20 served by server 2
57.852 20 done serving by server 2
[6.025 19 1]
```

#### Way 4
Input to the program comprises (in order of presentation):
* an int value denoting the base seed for the RandomGenerator object;
* an int value representing the number of servers
* an int value representing the number of self-checkout counters, Nself
* an int value for the maximum queue length, Qmax
* an int representing the number of customers (or the number of arrival events) to simulate
* a positive double parameter for the arrival rate, λ
* a positive double parameter for the service rate, μ
* a positive double parameter for the resting rate, ρ
* a double parameter for the probability of resting, Pr

The following is a sample run for way 4.
```
$ java Main 1 2 1 2 20 1.0 1.0 0.1 0.5
0.000 1 arrives
0.000 1 served by server 1
0.313 1 done serving by server 1
0.314 2 arrives
0.314 2 served by server 1
0.417 2 done serving by server 1
1.205 3 arrives
1.205 3 served by server 2
1.904 3 done serving by server 2
2.776 4 arrives
2.776 4 served by server 2
2.791 4 done serving by server 2
3.877 5 arrives
3.877 5 served by server 1
3.910 6 arrives
3.910 6 served by server 2
3.922 6 done serving by server 2
4.031 5 done serving by server 1
9.006 7 arrives
9.006 7 served by server 1
9.043 8 arrives
9.043 8 served by server 2
9.105 9 arrives
9.105 9 served by self-check 3
9.160 10 arrives
9.160 10 waits to be served by server 1
9.225 11 arrives
9.225 11 waits to be served by server 1
9.402 9 done serving by self-check 3
10.148 12 arrives
10.148 12 served by self-check 3
10.200 12 done serving by self-check 3
10.484 7 done serving by server 1
10.484 10 served by server 1
11.205 13 arrives
11.205 13 served by self-check 3
11.574 13 done serving by self-check 3
11.636 8 done serving by server 2
12.429 14 arrives
12.429 14 served by self-check 3
12.589 14 done serving by self-check 3
13.109 15 arrives
13.109 15 served by self-check 3
13.974 10 done serving by server 1
13.974 11 served by server 1
14.869 11 done serving by server 1
15.264 16 arrives
15.264 16 served by server 1
15.524 17 arrives
15.524 17 served by server 2
15.531 17 done serving by server 2
15.940 18 arrives
15.940 18 waits to be served by server 1
15.978 15 done serving by self-check 3
16.307 16 done serving by server 1
16.307 18 served by server 1
16.883 18 done serving by server 1
17.793 19 arrives
17.793 19 served by server 1
18.535 19 done serving by server 1
18.765 20 arrives
18.765 20 served by server 1
21.773 20 done serving by server 1
[0.322 20 0]
```

#### Way 5
Input to the program comprises (in order of presentation):
* an int value denoting the base seed for the RandomGenerator object;
* an int value representing the number of servers
* an int value representing the number of self-checkout counters, Nself
* an int value for the maximum queue length, Qmax
* an int representing the number of customers (or the number of arrival events) to simulate
* a positive double parameter for the arrival rate, λ
* a positive double parameter for the service rate, μ
* a positive double parameter for the resting rate, ρ
* a double parameter for the probability of resting, Pr
* a double parameter for the probability of a greedy customer occurring, Pg

The following is a sample run for way 5.
```
$ java Main 1 2 1 2 20 1.0 1.0 0.1 0.5 0.9
0.000 1(greedy) arrives
0.000 1(greedy) served by server 1
0.313 1(greedy) done serving by server 1
0.314 2(greedy) arrives
0.314 2(greedy) served by server 1
0.417 2(greedy) done serving by server 1
1.205 3(greedy) arrives
1.205 3(greedy) served by server 2
1.904 3(greedy) done serving by server 2
2.776 4(greedy) arrives
2.776 4(greedy) served by server 2
2.791 4(greedy) done serving by server 2
3.877 5(greedy) arrives
3.877 5(greedy) served by server 1
3.910 6(greedy) arrives
3.910 6(greedy) served by server 2
3.922 6(greedy) done serving by server 2
4.031 5(greedy) done serving by server 1
9.006 7(greedy) arrives
9.006 7(greedy) served by server 1
9.043 8(greedy) arrives
9.043 8(greedy) served by server 2
9.105 9(greedy) arrives
9.105 9(greedy) served by self-check 3
9.160 10 arrives
9.160 10 waits to be served by server 1
9.225 11(greedy) arrives
9.225 11(greedy) waits to be served by server 2
9.402 9(greedy) done serving by self-check 3
10.148 12(greedy) arrives
10.148 12(greedy) served by self-check 3
10.200 12(greedy) done serving by self-check 3
10.484 7(greedy) done serving by server 1
10.484 10 served by server 1
11.205 13(greedy) arrives
11.205 13(greedy) served by self-check 3
11.574 13(greedy) done serving by self-check 3
11.636 8(greedy) done serving by server 2
12.429 14(greedy) arrives
12.429 14(greedy) served by self-check 3
12.589 14(greedy) done serving by self-check 3
13.109 15(greedy) arrives
13.109 15(greedy) served by self-check 3
13.974 10 done serving by server 1
15.264 16 arrives
15.264 16 served by server 1
15.500 11(greedy) served by server 2
15.524 17(greedy) arrives
15.524 17(greedy) waits to be served by server 1
15.940 18(greedy) arrives
15.940 18(greedy) waits to be served by server 2
15.978 15(greedy) done serving by self-check 3
16.159 16 done serving by server 1
16.159 17(greedy) served by server 1
16.166 17(greedy) done serving by server 1
16.543 11(greedy) done serving by server 2
16.543 18(greedy) served by server 2
17.119 18(greedy) done serving by server 2
17.793 19(greedy) arrives
17.793 19(greedy) served by server 2
18.535 19(greedy) done serving by server 2
18.765 20(greedy) arrives
18.765 20(greedy) served by server 2
21.773 20(greedy) done serving by server 2
[0.442 20 0]
```