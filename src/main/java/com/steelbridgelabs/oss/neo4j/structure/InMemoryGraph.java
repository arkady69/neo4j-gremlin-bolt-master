package com.steelbridgelabs.oss.neo4j.structure;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.core.NodeProxy;
import org.neo4j.kernel.impl.core.RelationshipProxy;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class InMemoryGraph implements Graph {
    private final GraphDatabaseService graphDb;

    public InMemoryGraph(File dbPath) {
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
    }

    @Override
    public Vertex addVertex(Object... keyValues) {
        Result result = execute("create (n) return n");
        Map<String, Object> next = result.next();

        NodeProxy nodeProxy = NodeProxy.class.cast(next.get("n"));

        return new InMemoryVertex(nodeProxy, this);
    }

    @Override
    public <C extends GraphComputer> C compute(Class<C> graphComputerClass) throws IllegalArgumentException {
        return null;
    }

    @Override
    public GraphComputer compute() throws IllegalArgumentException {
        return null;
    }

    @Override
    public Iterator<Vertex> vertices(Object... vertexIds) {
        final String query = String.format("match (a) where id(a) in [%s] return a", StringUtils.join(vertexIds, ","));

        return executeForNodeStream(query).map(x->(Vertex) x).iterator();
    }

    @Override
    public Iterator<Edge> edges(Object... edgeIds) {
        throw new RuntimeException();
    }

    @Override
    public Transaction tx() {
        return new FakeTransaction();
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Variables variables() {
        return null;
    }

    @Override
    public Configuration configuration() {
        return null;
    }

    public Result execute(String cypherQuery) {
        return graphDb.execute(cypherQuery);
    }

    public Stream<InMemoryEdge> executeForEdgeStream(String cypherQuery) {
        Result results = graphDb.execute(cypherQuery);

        return results.stream()
                .map(Map::values).flatMap(Collection::stream).map(x-> new InMemoryEdge((RelationshipProxy) x, this));
    }

    public Stream<InMemoryVertex> executeForNodeStream(String cypherQuery) {
        Result results = graphDb.execute(cypherQuery);

        return results.stream()
                .map(Map::values).flatMap(Collection::stream).map(x -> new InMemoryVertex((NodeProxy) x, this));
    }
}

