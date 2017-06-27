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
package ch.jug.coma.business.sigs.control;

import ch.jug.coma.PersistenceManager;
import ch.jug.coma.business.sigs.entity.SIG;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class SIGService {

    private Comparator<SIG> sortByName = (s1, s2) -> s1.getName().toLowerCase().compareTo(s2.getName().toLowerCase());

    private final Datastore datastore;

    public SIGService() {
        this.datastore = PersistenceManager.getDatastore();
    }

    public List<SIG> readAllSIGs() {
        return this.datastore.createQuery(SIG.class).asList().stream()
                .sorted(sortByName)
                .collect(Collectors.toList());
    }

    public String createSIG(final SIG sig) {
        this.datastore.save(sig);
        return sig.getId();
    }

    public void deleteSIG(final String id) {
        final Query<SIG> query = datastore.createQuery(SIG.class)
                .filter("_id =", new ObjectId(id));
        final WriteResult result = this.datastore.delete(query);
    }

}
