package graph;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Scanner;

public class ListGraph extends Graph {

    public ListGraph(Integer nodes, Scanner scanner) {
        super(nodes);
        int lines = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < lines; i++) {
            var edge = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).toList();
            edges.add(Pair.of(edge.get(0), edge.get(1)));
        }
    }
}
