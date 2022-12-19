package graph;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Scanner;

public class MatrixGraph extends Graph {

    public MatrixGraph(Integer nodes, Scanner scanner) {
        super(nodes);

        for (int i = 0; i < nodes; i++) {
            var row = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).toList();
            for (int j = i + 1; j < nodes; j++) {
                if (row.get(j) == 1) {
                    edges.add(Pair.of(i, j));
                }
            }
        }
    }
}
