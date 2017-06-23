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
package ch.jug.coma.business.event.control;

import ch.jug.coma.PersistenceManager;
import ch.jug.coma.business.event.entity.Event;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EventService {

    private final Datastore datastore;

    public EventService() {
        this.datastore = PersistenceManager.getDatastore();
    }

    public List<Event> readAllEvents() {
        return this.datastore.createQuery(Event.class).asList();
    }

    public List<Event> readEventsForCity(final String city) {
        return readAllEvents().stream()
                .filter(e -> e.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public String createEvent(final Event event) {
        this.datastore.save(event);
        return event.getId();
    }

    public void deleteEvent(final String id) {
        final Query<Event> query = datastore.createQuery(Event.class)
                .filter("_id =", new ObjectId(id));
        final WriteResult result = this.datastore.delete(query);
    }
}
