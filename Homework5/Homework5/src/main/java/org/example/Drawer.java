package org.example;

import drawing.Drawing;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.sin;

public class Drawer {

    public static void draw(Drawing drawing, Graph graph) {
        var width = drawing.getWidth();
        var height = drawing.getHeight();

        Double radius = ((double) min(width, height)) / 3;
        var center = new Node(width.doubleValue() / 2, height.doubleValue() / 2);
        if (graph.getNodes() == 1) {
            drawing.drawNode(center, 10L);
            drawing.show();
            return;
        }

        var nodes = new ArrayList<Node>();
        for (int i = 0; i < graph.getNodes(); i++) {
            nodes.add(makeNode(center, radius, i, graph.getNodes()));
        }

        nodes.forEach(node -> drawing.drawNode(node, 10L));

        graph.getEdges().forEach(edge -> {
            var from = nodes.get(edge.getLeft());
            var to = nodes.get(edge.getRight());
            drawing.drawLine(from, to);
        });

        drawing.show();
    }

    private static Node makeNode(Node center, Double radius, Integer vertex, Integer size) {
        double angle = 2 * Math.PI * vertex / size;
        return new Node(center.x() + radius * cos(angle), center.y() + radius * sin(angle));
    }
}
