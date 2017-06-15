/*
 * CoMa – Open Source Community Manager
 * Copyright (C) 2017 Java User Group Switzerland
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ch.jug.coma;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import lombok.Synchronized;
import lombok.experimental.UtilityClass;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;

@UtilityClass
public class PersistenceManager {

    private static Datastore datastore = null;

    @Synchronized
    private static void init() {
        if (datastore == null) {
            final String dbUsername = System.getenv("db_username");
            final String dbPassword = System.getenv("db_password");
            final String dbHost = System.getenv("db_host");
            final int dbPort = Integer.parseInt(System.getenv("db_port"));
            final String dbName = System.getenv("db_name");

            final String dbURI = String.format(
                    "mongodb://%s:%s@%s:%d/%s",
                    dbUsername, dbPassword, dbHost, dbPort, dbName
            );

            final MongoClientURI mongoClientURI = new MongoClientURI(dbURI);
            final MongoClient mongoClient = new MongoClient(mongoClientURI);

            final Morphia morphia = new Morphia();
            final Mapper mapper = morphia.getMapper();
            mapper.getOptions().setObjectFactory(new CustomMorphiaObjectFactory());
            morphia.mapPackage("ch.jug.coma.business.event.entity");

            datastore = morphia.createDatastore(mongoClient, dbName);
            datastore.ensureIndexes();
        }
    }

    public static <T> Datastore getDatastore() {
        if (datastore == null) {
            init();
        }

        return datastore;
    }

}
