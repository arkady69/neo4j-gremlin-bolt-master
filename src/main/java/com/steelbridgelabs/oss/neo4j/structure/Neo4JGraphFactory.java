/*
 *  Copyright 2016 SteelBridge Laboratories, LLC.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  For more information: http://steelbridgelabs.com
 */

package com.steelbridgelabs.oss.neo4j.structure;

import com.steelbridgelabs.oss.neo4j.structure.partitions.AnyLabelReadPartition;
import com.steelbridgelabs.oss.neo4j.structure.partitions.NoReadPartition;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

/**
 * @author Rogelio J. Baucells
 */
public class Neo4JGraphFactory {

    public static Graph open(Configuration configuration) {
        if (configuration == null)
            throw Graph.Exceptions.argumentCanNotBeNull("configuration");
        try {
            // neo4j driver configuration
            Config config = Config.build()
                .toConfig();
            // graph name
            String graphName = configuration.getString(Neo4JGraphConfigurationBuilder.Neo4JGraphNameConfigurationKey);
            // create driver instance
            Driver driver = GraphDatabase.driver(configuration.getString(Neo4JGraphConfigurationBuilder.Neo4JUrlConfigurationKey), AuthTokens.basic(configuration.getString(Neo4JGraphConfigurationBuilder.Neo4JUsernameConfigurationKey), configuration.getString(Neo4JGraphConfigurationBuilder.Neo4JPasswordConfigurationKey)), config);
            // create providers
            Neo4JElementIdProvider<?> vertexIdProvider = loadProvider(configuration.getString(Neo4JGraphConfigurationBuilder.Neo4JVertexIdProviderClassNameConfigurationKey));
            Neo4JElementIdProvider<?> edgeIdProvider = loadProvider(configuration.getString(Neo4JGraphConfigurationBuilder.Neo4JEdgeIdProviderClassNameConfigurationKey));
            // check a read partition is required
            if (graphName != null)
                return new Neo4JGraph(new AnyLabelReadPartition(graphName), new String[]{graphName}, driver, vertexIdProvider, edgeIdProvider, configuration);
            // no partition
            return new Neo4JGraph(new NoReadPartition(), new String[]{}, driver, vertexIdProvider, edgeIdProvider, configuration);
        }
        catch (Throwable ex) {
            // throw runtime exception
            throw new RuntimeException("Error creating Graph instance from configuration", ex);
        }
    }

    private static Neo4JElementIdProvider<?> loadProvider(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // check class name
        if (className != null) {
            // load class
            Class<?> type = Class.forName(className);
            // create instance
            return (Neo4JElementIdProvider<?>)type.newInstance();
        }
        return null;
    }
}
