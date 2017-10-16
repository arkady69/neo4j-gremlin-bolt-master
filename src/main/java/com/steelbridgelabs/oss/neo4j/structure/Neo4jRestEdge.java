package com.steelbridgelabs.oss.neo4j.structure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.apache.tinkerpop.gremlin.structure.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Neo4jRestEdge implements Edge {
    private Long id;
    private String label;
    private Map<String, Object> properties;
    private Long startNode;
    private Long endNode;


    @Override
    public Iterator<Vertex> vertices(Direction direction) {
        return null;
    }

    @Override
    public Object id() {
        return id;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public Graph graph() {
        return null;
    }

    @Override
    public <V> Property<V> property(String key, V value) {
        return null;
    }

    @Override
    public void remove() {
        throw new NotImplementedException();
    }

    @Override
    public <V> Iterator<Property<V>> properties(String... propertyKeys) {
        List<Property<V>> list = new ArrayList<>();

        for (String key : propertyKeys) {
            if (properties.containsKey(key)) {
                val value = properties.get(key);

                list.add(new Neo4jRestProperty<V>(key, (V) value));
            }
        }

        return list.iterator();
    }
}
