package com.steelbridgelabs.oss.neo4j.structure;

import lombok.Data;
import lombok.val;
import org.apache.tinkerpop.gremlin.structure.*;
import org.neo4j.graphdb.Result;
import org.neo4j.helpers.collection.Iterators;
import org.neo4j.kernel.impl.core.NodeProxy;

import java.util.*;
import java.util.stream.Collectors;


@Data
public class InMemoryVertex implements Vertex {
    private final NodeProxy node;
    private final InMemoryGraph graph;

    @Override
    public Edge addEdge(String label, Vertex inVertex, Object... keyValues) {
        val query = String.format("MATCH (n),(m) where id(n) = %d and id(m) = %d CREATE (n)-[:%s]->(m)", node.getId(), inVertex.id(), label);

        Result result = graph.execute(query);

        return null;
    }

    @Override
    public <V> VertexProperty<V> property(VertexProperty.Cardinality cardinality, String key, V value, Object... keyValues) {
        String valuePart;
        if (value instanceof String) {
            valuePart = String.format("'%s'", value);
        }
        else if (value instanceof Number) {
            valuePart = String.format("%s", value);
        }
        else {
            throw new RuntimeException("Unknown type");
        }


        val query = String.format("match (n) where id(n) = %d set n.%s = %s", node.getId(), key, valuePart);

        graph.execute(query);

        return null;
    }

    @Override
    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        final String relationships = Arrays.stream(edgeLabels).map(x -> ":" + x).collect(Collectors.joining("|"));

        String query;
        if (Direction.IN == direction) {
            query = String.format("match (n)<-[r%s]-() where id(n) = %d return r", relationships, node.getId());
        }
        else if (Direction.OUT == direction) {
            query = String.format("match (n)-[r%s]->() where id(n) = %d return r", relationships, node.getId());
        }
        else {
            query = String.format("match (n)-[r%s]-() where id(n) = %d return r", relationships, node.getId());
        }

        Result execute = graph.execute(query);

        return Iterators.emptyIterator();

    }

    @Override
    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
        throw new RuntimeException("vertices");

    }

    @Override
    public Object id() {
        return node.getId();
    }

    @Override
    public String label() {
        throw new RuntimeException("label");

    }

    @Override
    public Graph graph() {
        return graph;

    }

    @Override
    public void remove() {

    }

    @Override
    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        List<String> keys = Arrays.asList(propertyKeys);

        String cypherQuery = String.format("match (n) where id(n)=%d return properties(n) as props", node.getId());
        Result results = graph.execute(cypherQuery);

        final ArrayList<VertexProperty<V>> list = new ArrayList<>();

        if (!results.hasNext()) return list.iterator();

        val row = results.next();
        val column = row.entrySet().iterator().next();
        val value = (Map<String, String>) column.getValue();

        if (!keys.isEmpty()) {
            for (String pk : propertyKeys) {
                if (value.containsKey(pk))
                    list.add(new ImemoryVertexProperty<>(pk, (V) value.get(pk)));
            }
        } else {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                list.add(new ImemoryVertexProperty<>(entry.getKey(), (V) entry.getValue()));
            }
        }

        return list.iterator();
    }

    @Override
    public <V> VertexProperty<V> property(String key, V value) {
        String foo = value instanceof String ? String.format("'%s'", value) : String.format("%s", value);

        String query = String.format("MATCH (n) where ID(n)= %d SET n.%s = %s", node.getId(), key, foo);

        graph.execute(query);

        return new ImemoryVertexProperty<>(key, value);

    }
}
