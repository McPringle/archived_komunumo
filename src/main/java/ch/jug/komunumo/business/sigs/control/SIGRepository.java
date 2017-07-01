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

import ch.jug.komunumo.business.sigs.entity.SIG;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

class SIGRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, SIG> siga = new ConcurrentHashMap<>();

    SIG create(@NotNull final SIG sig) {
        final String id = UUID.randomUUID().toString();
        final long version = sig.hashCode();
        final SIG sigToCreate = sig.toBuilder()
                .id(id)
                .version(version)
                .build();
        siga.put(id, sigToCreate);
        return sigToCreate;
    }

    List<SIG> readAll() {
        return siga.values().stream()
                .sorted(Comparator.comparing(SIG::getName))
                .collect(toList());
    }

    Optional<SIG> readWithId(@NotNull final String id) {
        return Optional.ofNullable(siga.get(id));
    }

    void delete(@NotNull final String id) {
        siga.remove(id);
    }
}
