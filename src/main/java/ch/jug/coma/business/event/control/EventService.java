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
import pl.setblack.airomem.core.PersistenceController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class EventService {

    private PersistenceController<EventRepository> controller;

    @PostConstruct
    public void setupResources() {
        controller = PersistenceManager.createController(Event.class, EventRepository::new);
    }

    @PreDestroy
    public void cleanupResources() {
        controller.close();
    }

    public Event create(@NotNull final Event event) {
        return controller.executeAndQuery(mgr -> mgr.create(event));
    }

    public List<Event> readAll() {
        return controller.query(EventRepository::readAll);
    }

    public Optional<Event> readWithId(@NotNull final String id) {
        return controller.query(ctrl -> ctrl.readWithId(id));
    }

    public List<Event> readWithCity(final String city) {
        return readAll().stream()
                .filter(e -> e.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public void delete(@NotNull final String id) {
        controller.execute(mgr -> mgr.delete(id));
    }

}
