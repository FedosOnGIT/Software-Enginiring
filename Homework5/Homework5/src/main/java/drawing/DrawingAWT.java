package drawing;

import graph.Node;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

@Getter
@Setter
public class DrawingAWT implements Drawing {
    private final Long width;
    private final Long height;
    private final List<Ellipse2D.Double> nodes;
    private final List<Line2D.Double> edges;

    public DrawingAWT(Long width, Long height) {
        this.width = width;
        this.height = height;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }


    @Override
    public void drawNode(Node center, Long radius) {
        nodes.add(new Ellipse2D.Double(center.x() - radius,
                center.y() - radius,
                2 * radius,
                2 * radius));
    }

    @Override
    public void drawLine(Node from, Node to) {
        edges.add(new Line2D.Double(from.x(),
                from.y(),
                to.x(),
                to.y()));
    }

    @Override
    public void show() {
        var frame = new Frame() {
            @Override
            public void paint(Graphics g) {
                Graphics2D graphics = (Graphics2D) g;
                edges.forEach(graphics::draw);
                nodes.forEach(graphics::fill);
            }
        };
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit(0);
            }
        });
        frame.setSize(width.intValue(), height.intValue());
        frame.setVisible(true);
    }
}
