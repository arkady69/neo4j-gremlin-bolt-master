package com.steelbridgelabs.oss.neo4j.structure;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;

import java.util.function.Consumer;

public class FakeTransaction implements Transaction {
    @Override
    public void open() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public <G extends Graph> G createThreadedTx() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void readWrite() {

    }

    @Override
    public void close() {

    }

    @Override
    public Transaction onReadWrite(Consumer<Transaction> consumer) {
        return null;
    }

    @Override
    public Transaction onClose(Consumer<Transaction> consumer) {
        return null;
    }

    @Override
    public void addTransactionListener(Consumer<Status> listener) {

    }

    @Override
    public void removeTransactionListener(Consumer<Status> listener) {

    }

    @Override
    public void clearTransactionListeners() {

    }
}
