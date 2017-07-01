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
package ch.jug.komunumo.business.sigs.control;

import ch.jug.komunumo.PersistenceManager;
import ch.jug.komunumo.business.backup.entity.BackupData;
import ch.jug.komunumo.business.sigs.entity.SIG;
import pl.setblack.airomem.core.PersistenceController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class SIGService implements BackupData {

    private PersistenceController<SIGRepository> controller;

    @PostConstruct
    public void setupResources() {
        controller = PersistenceManager.createController(SIG.class, SIGRepository::new);
    }

    @PreDestroy
    public void cleanupResources() {
        controller.close();
    }

    public SIG create(@NotNull final SIG sig) {
        return controller.executeAndQuery(mgr -> mgr.create(sig));
    }

    public List<SIG> readAll() {
        return controller.query(SIGRepository::readAll);
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
}
