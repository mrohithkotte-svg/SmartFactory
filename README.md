# SmartFactory – Industrial Production Analytics Platform (Modular CO1–CO6)

SmartFactory is an industrial production analytics and optimization platform represented through a console terminal interface. The platform models factory machines, maintenance fault logs, technical documentation, material workflows, and real-time IoT telemetry, analyzing them through fundamental Computer Science and Data Structures & Algorithms (CO1 to CO6).

The project is structured into a **modular, multi-package architecture** with clean separation of concerns, beginner-friendly code, and strict adherence to standard Java without external dependencies.

---

## 1. Project Package Structure

```text
SmartFactory_Terminal_DSA3_CO1_CO3/
├── src/
│   └── smartfactory/
│       ├── Main.java                          <-- Main application entry point
│       ├── menu/
│       │   └── MainMenu.java                  <-- Interactive terminal main menu
│       ├── model/
│       │   ├── Machine.java                   <-- Factory machine entity
│       │   ├── Report.java                    <-- Incident & maintenance report entity
│       │   └── Document.java                  <-- Technical documentation entity
│       ├── data/
│       │   └── FactoryData.java               <-- In-memory repository & factory corpus
│       ├── util/
│       │   └── InputUtils.java                <-- Reusable console I/O, headers, and pause
│       ├── co1/                               <-- CO1: String-Matching Algorithms
│       │   ├── KMP.java                       <-- Knuth-Morris-Pratt pattern search
│       │   ├── ZFunction.java                 <-- Z-algorithm prefix search
│       │   ├── RabinKarp.java                 <-- Rolling-hash string search
│       │   ├── AhoCorasick.java               <-- Multi-pattern fault keyword detector
│       │   └── StringAlgorithmsMenu.java      <-- CO1 interactive submenu
│       ├── co2/                               <-- CO2: Suffix Structures
│       │   ├── SuffixArray.java               <-- Prefix-doubling O(N log^2 N) suffix array
│       │   ├── SAIS.java                      <-- Induced Sorting O(N) suffix array
│       │   ├── LCPKasai.java                  <-- Linear-time Kasai LCP algorithm
│       │   ├── SuffixAutomaton.java           <-- Directed Acyclic Word Graph (SAM)
│       │   └── SuffixStructuresMenu.java      <-- CO2 interactive submenu
│       ├── co3/                               <-- CO3: Advanced Dynamic Programming
│       │   ├── EditDistance.java              <-- Levenshtein & Damerau-Levenshtein
│       │   ├── WeightedEditDistance.java      <-- Custom cost penalty edit distance
│       │   ├── MatrixChainMultiplication.java <-- Matrix chain multiplication ordering
│       │   ├── BitmaskDP.java                 <-- Machine selection under resource limit
│       │   ├── SubsetDP.java                  <-- TSP optimal maintenance inspection route
│       │   └── DynamicProgrammingMenu.java    <-- CO3 interactive submenu
│       ├── co4/                               <-- CO4: Network Flow Algorithms
│       │   ├── FordFulkerson.java             <-- DFS augmenting path max flow
│       │   ├── EdmondsKarp.java               <-- BFS shortest-path max flow
│       │   ├── Dinic.java                     <-- Level graph & blocking flow
│       │   ├── BipartiteMatching.java         <-- Worker-to-machine assignment
│       │   ├── MaxFlowMinCut.java             <-- S-T reachability bottleneck cut
│       │   └── NetworkFlowMenu.java           <-- CO4 interactive submenu
│       ├── co5/                               <-- CO5: NP-Completeness & Approximation
│       │   ├── SATSolverDemo.java             <-- 3-CNF constraint evaluation
│       │   ├── ThreeSATToClique.java          <-- 3-SAT to CLIQUE graph reduction
│       │   ├── CliqueToIndependentSet.java    <-- Graph complement transformation
│       │   ├── IndependentSetToVertexCover.java <-- Duality mapping (S <-> V \ S)
│       │   ├── VertexCoverApproximation.java  <-- Maximal matching 2-approximation
│       │   └── NPCompletenessMenu.java        <-- CO5 interactive submenu
│       └── co6/                               <-- CO6: Randomized & Parallel Algorithms
│           ├── RandomizedQuickSort.java       <-- Order prioritization with random pivot
│           ├── MillerRabin.java               <-- Probabilistic primality test
│           ├── ReservoirSampling.java         <-- Single-pass IoT stream sampling
│           ├── BlellochScan.java              <-- Parallel up-sweep/down-sweep prefix sum
│           ├── ParallelReduce.java            <-- Multi-threaded ForkJoinPool reduction
│           ├── BrentsTheorem.java             <-- Theoretical parallel work-depth bounds
│           └── RandomizedParallelMenu.java    <-- CO6 interactive submenu
├── data/
│   └── README.txt
├── README.md
└── .gitignore
```

---

## 2. Compilation and Execution

### Prerequisites
- JDK 17 or higher (tested with JDK 25 LTS).

### Compilation
From the project root directory:

**Windows (PowerShell / Command Prompt):**
```powershell
javac -encoding UTF-8 (Get-ChildItem -Recurse -Filter *.java src).FullName
```
or
```cmd
javac -encoding UTF-8 src/smartfactory/*.java src/smartfactory/*/*.java
```

**Linux / macOS:**
```bash
find src -name "*.java" > sources.txt
javac -encoding UTF-8 @sources.txt
```

### Running the Application
```bash
java -cp src smartfactory.Main
```

---

## 3. Package Responsibilities & Algorithms

### `smartfactory`
- **`Main.java`**: Lightweight application entry point that bootstraps and calls the main terminal menu.

### `smartfactory.menu`
- **`MainMenu.java`**: Top-level interactive navigation menu routing users to CO1 through CO6 submenus, Factory Dashboard, and Factory Data views.

### `smartfactory.co1` (String Algorithms)
- **`KMP.java`**: Knuth-Morris-Pratt pattern search using LPS table ($O(N + M)$).
- **`ZFunction.java`**: Linear-time Z-algorithm prefix match locator.
- **`RabinKarp.java`**: Rolling-hash pattern matching for inventory and part numbers.
- **`AhoCorasick.java`**: Multi-keyword simultaneous fault detection (`overheating`, `vibration`, `leakage`).
- **`StringAlgorithmsMenu.java`**: Console submenu for CO1 queries.

### `smartfactory.co2` (Suffix Structures)
- **`SuffixArray.java`**: Prefix-doubling suffix array index construction ($O(N \log^2 N)$).
- **`SAIS.java`**: Linear-time Induced Sorting suffix array construction ($O(N)$).
- **`LCPKasai.java`**: Linear-time Kasai algorithm computing Longest Common Prefixes.
- **`SuffixAutomaton.java`**: Minimal Directed Acyclic Word Graph (DAWG) evaluating substring occurrence count in $O(|pattern|)$.
- **`SuffixStructuresMenu.java`**: Console submenu for CO2 queries.

### `smartfactory.co3` (Advanced Dynamic Programming)
- **`EditDistance.java`**: Levenshtein Distance and Damerau-Levenshtein Distance (with character transposition).
- **`WeightedEditDistance.java`**: Edit distance with parameterized insertion, deletion, and substitution weights.
- **`MatrixChainMultiplication.java`**: Computes optimal matrix multiplication parenthesization for multi-stage data pipelines.
- **`BitmaskDP.java`**: Optimal subset machine selection under resource capacity constraints ($O(2^N)$).
- **`SubsetDP.java`**: Travelling Salesperson Problem (TSP) dynamic programming finding the minimum-cost maintenance route.
- **`DynamicProgrammingMenu.java`**: Console submenu for CO3 queries.

### `smartfactory.co4` (Network Flow Algorithms)
- **`FordFulkerson.java`**: Maximum material flow using DFS augmenting paths on residual capacity graphs.
- **`EdmondsKarp.java`**: Maximum material flow using BFS shortest-path search ($O(V \cdot E^2)$).
- **`Dinic.java`**: Maximum flow via BFS Level Graph construction and DFS Blocking Flow augmentation ($O(V^2 \cdot E)$).
- **`BipartiteMatching.java`**: Maximum cardinality bipartite matching assigning operators to compatible machines.
- **`MaxFlowMinCut.java`**: Computes maximum flow, extracts the residual $S$-$T$ partition, identifies saturated bottleneck links, and confirms $\text{Max Flow} = \text{Min Cut Capacity}$.
- **`NetworkFlowMenu.java`**: Shared 6-node factory production network and CO4 interactive console submenu.

### `smartfactory.co5` (NP-Completeness and Approximation)
- **`SATSolverDemo.java`**: Boolean production safety rules expressed as 3-CNF clauses with truth assignment evaluation.
- **`ThreeSATToClique.java`**: Polynomial-time reduction constructing a 9-vertex graph from 3 clauses where a satisfying assignment forms a 3-clique.
- **`CliqueToIndependentSet.java`**: Graph complement transformation ($G \iff \overline{G}$) proving clique/independent set duality.
- **`IndependentSetToVertexCover.java`**: Complementary vertex set mapping proving that $S$ is an independent set if and only if $V \setminus S$ covers all edges.
- **`VertexCoverApproximation.java`**: Polynomial-time 2-approximation algorithm for sensor placement using maximal edge matching ($|C| \le 2 \cdot \text{OPT}$).
- **`NPCompletenessMenu.java`**: Console submenu for CO5 queries.

### `smartfactory.co6` (Randomized & Parallel Algorithms)
- **`RandomizedQuickSort.java`**: Uniform random pivot QuickSort preventing worst-case $O(N^2)$ execution on priority queues.
- **`MillerRabin.java`**: Monte Carlo probabilistic primality test using `BigInteger` modular exponentiation for cryptographic key verification.
- **`ReservoirSampling.java`**: Online stream sampling maintaining an unbiased sample of size $k$ without storing the entire stream.
- **`BlellochScan.java`**: Work-efficient parallel exclusive prefix sum using Up-Sweep and Down-Sweep tree passes with power-of-two padding.
- **`ParallelReduce.java`**: Multi-threaded telemetry aggregation across 500,000 records using `ForkJoinPool` and `RecursiveTask`.
- **`BrentsTheorem.java`**: Theoretical speedup and efficiency analysis under the bound $T(P) \le \frac{W - D}{P} + D$.
- **`RandomizedParallelMenu.java`**: Console submenu for CO6 queries.

### `smartfactory.util`
- **`InputUtils.java`**: Shared console I/O, prompt handling, banner headers, pause prompts, and array formatting.

---

## 4. Test Verification Summary

| CO Module | Tested Classes | Representative Execution | Verification Result |
|---|---|---|---|
| **CO1** | `KMP`, `ZFunction`, `RabinKarp`, `AhoCorasick` | Search machine `MTR-204` & multiple fault keywords | Matches found at exact corpus indices |
| **CO2** | `SuffixArray`, `SAIS`, `LCPKasai`, `SuffixAutomaton` | Indexed `DOC-001`, verified LCP and substring counts | Sorted suffixes and repeated prefixes confirmed |
| **CO3** | `EditDistance`, `MatrixChainMultiplication`, `BitmaskDP`, `SubsetDP` | Fuzzy match `MTR240`, matrix dimensions `10,20,30,40`, capacity `45` | Cost = 3, optimal parenthesization & route verified |
| **CO4** | `FordFulkerson`, `EdmondsKarp`, `Dinic`, `BipartiteMatching`, `MaxFlowMinCut` | Run all 3 flow algorithms & min-cut on 6-node network | All 3 returned **19 units/hr**; cut capacity **19**; matching **5/5** |
| **CO5** | `SATSolverDemo`, `ThreeSATToClique`, `CliqueToIndependentSet`, `IndependentSetToVertexCover`, `VertexCoverApproximation` | 3-SAT evaluations, graph reductions, 7-node pipeline cover | Satisfied/unsatisfied verified; 100% edges covered |
| **CO6** | `RandomizedQuickSort`, `MillerRabin`, `ReservoirSampling`, `BlellochScan`, `ParallelReduce`, `BrentsTheorem` | Quicksort with duplicate priorities, primes (97, 2147483647), scan, 500k reduce | Non-decreasing order verified; primality 100% correct; `seqSum == parSum` |
| **Menu** | `MainMenu` | Navigation between all submenus, back options, invalid inputs | Seamless menu flow without runtime exceptions |

---

## 5. Technical Limitations

1. **NP-Completeness (CO5)**:
   - The reductions demonstrate structural equivalence and polynomial transformation. They do not claim to solve NP-complete problems in polynomial time on large formulas.
2. **Vertex-Cover Approximation (CO5)**:
   - The 2-approximation algorithm guarantees $|C| \le 2 \cdot \text{OPT}$. It does not guarantee finding the absolute minimum vertex cover.
3. **Miller-Rabin Test (CO6)**:
   - Miller-Rabin is a Monte Carlo randomized algorithm. Composites are detected with 100% certainty (witness found), while primes pass $k = 15$ rounds with an error probability bound $< 4^{-15} \approx 9.3 \times 10^{-10}$.
4. **Parallel Overhead (CO6)**:
   - For small datasets ($< 100,000$ elements), the thread-spawning and synchronization overhead of `ForkJoinPool` can exceed a single-threaded loop.
