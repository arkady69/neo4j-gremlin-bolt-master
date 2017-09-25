package com.steelbridgelabs.oss.neo4j.structure;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

@Data
@AllArgsConstructor
public class ImemoryVertexProperty<V> implements VertexProperty<V>{
    private String key;
    private V value;

    @Override
    public String key() {
        return key;
    }

    @Override
    public V value() throws NoSuchElementException {
        return value;
    }

    @Override
    public boolean isPresent() {
        return value != null;
    }

    @Override
    public Vertex element() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove() {

    }

    @Override
    public Object id() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> Property<V> property(String key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <U> Iterator<Property<U>> properties(String... propertyKeys) {
        throw new UnsupportedOperationException();
    }

    public VertexProperty<V> asVertex() {
        return this;
    }
}
