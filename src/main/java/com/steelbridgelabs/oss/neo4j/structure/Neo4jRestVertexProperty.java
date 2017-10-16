package com.steelbridgelabs.oss.neo4j.structure;

import lombok.Data;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Data
public class Neo4jRestVertexProperty<V> implements VertexProperty<V> {
    private String key;
    private V value;


    public Neo4jRestVertexProperty(String key, V value) {
        this.key = key;
        this.value = value;
    }

    public Neo4jRestVertexProperty(String key) {
        this.key = key;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public V value() throws NoSuchElementException {
        if (isPresent()) return value;

        throw new NoSuchElementException();
    }

    @Override
    public boolean isPresent() {
        return value != null;
    }

    @Override
    public Vertex element() {
        throw new NotImplementedException();
    }

    @Override
    public void remove() {
        throw new NotImplementedException();
    }

    @Override
    public Object id() {
        throw new NotImplementedException();
    }

    @Override
    public <V> Property<V> property(String key, V value) {
        throw new NoSuchElementException();
    }

    @Override
    public <U> Iterator<Property<U>> properties(String... propertyKeys) {
        return null;
    }
}
