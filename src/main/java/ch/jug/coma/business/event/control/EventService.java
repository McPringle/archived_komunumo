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
import com.mongodb.client.MongoCollection;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class EventService {

    private final MongoCollection<Event> collection;

    public EventService() {
        this.collection = PersistenceManager.createMongoCollection(Event.class);
    }

    @GET
    public List<Event> readAllEvents() {
        final List<Event> events = new ArrayList<>();
        this.collection.find().iterator().forEachRemaining(events::add);
        return events;
    }

}
