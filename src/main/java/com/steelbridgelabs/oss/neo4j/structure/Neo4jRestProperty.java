package com.steelbridgelabs.oss.neo4j.structure;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Property;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.NoSuchElementException;

@Data
@AllArgsConstructor
public class Neo4jRestProperty<V> implements Property<V> {
    private String key;
    private V value;

    @Override
    public String key() {
        return null;
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
    public Element element() {
        throw new NotImplementedException();
    }

    @Override
    public void remove() {
        throw new NotImplementedException();

    }
}
