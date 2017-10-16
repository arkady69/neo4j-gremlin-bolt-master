package com.steelbridgelabs.oss.neo4j.structure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.apache.tinkerpop.gremlin.structure.*;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Neo4RestVertex implements Vertex {
    private Long id;
    private List<String> labels;
    private Map<String, Object> properties;

    @Override
    public Edge addEdge(String label, Vertex inVertex, Object... keyValues) {
        return null;
    }

    @Override
    public <V> VertexProperty<V> property(VertexProperty.Cardinality cardinality, String key, V value, Object... keyValues) {
        return null;
    }

    @Override
    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        return null;
    }

    @Override
    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
        return null;
    }

    @Override
    public Object id() {
        return id;
    }

    @Override
    public String label() {
        return labels.stream().map(x -> ":" + x).collect(Collectors.joining("|"));
    }

    @Override
    public Graph graph() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        List<VertexProperty<V>> list = new ArrayList<>();

        for (String key : propertyKeys) {
            if (properties.containsKey(key)) {
                val value = properties.get(key);

                list.add(new Neo4jRestVertexProperty<V>(key, (V) value));
            }
        }

        return list.iterator();
    }
}
