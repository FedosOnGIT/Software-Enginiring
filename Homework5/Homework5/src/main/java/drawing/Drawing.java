package drawing;

import graph.Node;

public interface Drawing {
    Long getWidth();

    Long getHeight();

    void drawNode(Node center, Long radius);

    void drawLine(Node from, Node to);

    void show();
}
