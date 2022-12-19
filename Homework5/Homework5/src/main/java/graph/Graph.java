package graph;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Graph {
    protected final Integer nodes;
    protected final List<Pair<Integer, Integer>> edges;

    protected Graph(Integer nodes) {
        this.nodes = nodes;
        this.edges = new ArrayList<>();
    }
}
