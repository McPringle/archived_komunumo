/*
 * Komunumo – Open Source Community Manager
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
package ch.jug.komunumo.business.fact.control;

import ch.jug.komunumo.PersistenceManager;
import ch.jug.komunumo.business.backup.entity.BackupAndRestore;
import ch.jug.komunumo.business.fact.entity.Fact;
import pl.setblack.airomem.core.PersistenceController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class FactService implements BackupAndRestore {

    private PersistenceController<FactRepository> controller;

    @PostConstruct
    public void setupResources() {
        controller = PersistenceManager.createController(Fact.class, FactRepository::new);
    }

    @PreDestroy
    public void cleanupResources() {
        controller.close();
    }

    public Fact create(@NotNull final Fact fact) {
        return controller.executeAndQuery(mgr -> mgr.create(fact));
    }

    public List<Fact> readAll() {
        return controller.query(FactRepository::readAll);
    }

    public Optional<Fact> readRandom() {
        return controller.query(FactRepository::readRandom);
    }

    public void delete(@NotNull final String id) {
        controller.execute(mgr -> mgr.delete(id));
    }

    @Override
    public List<Serializable> backup() {
        return readAll().stream()
                .map(e -> (Serializable) e)
                .collect(Collectors.toList());
    }

    @Override
    public void restore(final List<Serializable> data) {
        data.stream().map(f -> (Fact) f)
                .forEach(f -> controller.execute(mgr -> mgr.restore(f)));
    }

}
