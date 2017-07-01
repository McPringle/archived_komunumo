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
package ch.jug.komunumo.business.sponsor.control;

import ch.jug.komunumo.PersistenceManager;
import ch.jug.komunumo.business.backup.entity.BackupAndRestore;
import ch.jug.komunumo.business.sponsor.entity.Level;
import ch.jug.komunumo.business.sponsor.entity.Sponsor;
import pl.setblack.airomem.core.PersistenceController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class SponsorService implements BackupAndRestore {


    private PersistenceController<SponsorRepository> controller;

    @PostConstruct
    public void setupResources() {
        controller = PersistenceManager.createController(Sponsor.class, SponsorRepository::new);
    }

    @PreDestroy
    public void cleanupResources() {
        controller.close();
    }

    public Sponsor create(@NotNull final Sponsor sponsor) {
        return controller.executeAndQuery(mgr -> mgr.create(sponsor));
    }

    public List<Sponsor> readAll() {
        return controller.query(SponsorRepository::readAll);
    }

    public List<Sponsor> readActive() {
        return readAll().stream()
                .filter(Sponsor::getActive)
                .collect(Collectors.toList());
    }

    public List<Sponsor> readWithLevel(final Level level) {
        return readAll().stream()
                .filter(s -> s.getLevel().equals(level))
                .collect(Collectors.toList());
    }

    public List<Sponsor> readActiveWithLevel(final Level level) {
        return readWithLevel(level).stream()
                .filter(Sponsor::getActive)
                .collect(Collectors.toList());
    }

    public Sponsor update(@NotNull final Sponsor sponsor) {
        return controller.executeAndQuery(mgr -> mgr.update(sponsor));
    }

    @Override
    public List<Serializable> backup() {
        return readAll().stream()
                .map(s -> (Serializable) s)
                .collect(Collectors.toList());
    }

    @Override
    public void restore(final List<Serializable> data) {
        data.stream().map(s -> (Sponsor) s)
                .forEach(s -> controller.execute(mgr -> mgr.restore(s)));
    }

}
