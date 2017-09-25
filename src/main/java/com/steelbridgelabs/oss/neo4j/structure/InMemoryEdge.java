package com.steelbridgelabs.oss.neo4j.structure;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tinkerpop.gremlin.structure.*;
import org.neo4j.kernel.impl.core.RelationshipProxy;

import java.util.Iterator;

@Data
@AllArgsConstructor
public class InMemoryEdge implements Edge{
    private RelationshipProxy edge;
    private InMemoryGraph graph;

    @Override
    public Iterator<Vertex> vertices(Direction direction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object id() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String label() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Graph graph() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> Property<V> property(String key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove() {

    }

    @Override
    public <V> Iterator<Property<V>> properties(String... propertyKeys) {
        throw new UnsupportedOperationException();
    }
}
