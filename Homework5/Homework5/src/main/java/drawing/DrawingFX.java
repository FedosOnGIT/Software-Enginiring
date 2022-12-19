package drawing;

import graph.Node;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.System.exit;

@Getter
@Setter
public class DrawingFX implements Drawing {
    private final Canvas canvas;
    private final List<Consumer<GraphicsContext>> tasks;
    private final Long width;
    private final Long height;

    public DrawingFX(Long width, Long height) {
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        tasks = new ArrayList<>();
    }

    @Override
    public void drawNode(Node center, Long radius) {
        tasks.add(context -> context
                .fillOval(center.x() - radius,
                        center.y() - radius,
                        2 * radius,
                        2 * radius));
    }

    @Override
    public void drawLine(Node from, Node to) {
        tasks.add(context -> context.strokeLine(from.x(), from.y(), to.x(), to.y()));
    }

    @Override
    public void show() {
        var context = canvas.getGraphicsContext2D();
        tasks.forEach(task -> task.accept(context));
        var application = new Application() {

            @Override
            public void start(Stage stage) {
                var group = new Group();
                group.getChildren().add(canvas);
                stage.setScene(new Scene(group, Color.WHITE));
                stage.show();
                stage.setOnCloseRequest(windowEvent -> exit(0));
            }
        };
        Platform.startup(() -> application.start(new Stage()));
    }
}
