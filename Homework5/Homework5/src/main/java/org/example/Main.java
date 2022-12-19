package org.example;

import drawing.Drawing;
import drawing.DrawingAWT;
import drawing.DrawingFX;
import graph.Graph;
import graph.ListGraph;
import graph.MatrixGraph;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            throw new IllegalArgumentException("Need only 2 args: drawing type and graph type");
        }

        Drawing drawing = Objects.equals(args[0], "AWT")
                ? new DrawingAWT(800L, 800L)
                : new DrawingFX(800L, 800L);
        Graph graph;
        try (Scanner scanner = new Scanner(System.in)) {
            int nodes = Integer.parseInt(scanner.nextLine());
            graph = Objects.equals(args[1], "List")
                    ? new ListGraph(nodes, scanner)
                    : new MatrixGraph(nodes, scanner);

        }
        Drawer.draw(drawing, graph);
    }
}