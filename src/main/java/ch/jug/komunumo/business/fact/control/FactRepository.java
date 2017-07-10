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

import ch.jug.komunumo.business.fact.entity.Fact;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

class FactRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Fact> facts = new ConcurrentHashMap<>();

    Fact create(@NotNull final Fact fact) {
        final String id = UUID.randomUUID().toString();
        final long version = fact.hashCode();
        final Fact factToCreate = fact.toBuilder()
                .id(id)
                .version(version)
                .build();
        facts.put(id, factToCreate);
        return factToCreate;
    }

    void restore(final Fact fact) {
        facts.putIfAbsent(fact.getId(), fact);
    }

    List<Fact> readAll() {
        return new ArrayList<>(facts.values());
    }

    Optional<Fact> readRandom() {
        final int size = facts.size();
        if (size > 0) {
            final int randomIndex = ThreadLocalRandom.current().nextInt(0, size);
            final Fact fact = this.facts.values().toArray(new Fact[size])[randomIndex];
            return Optional.ofNullable(fact);
        }
        return Optional.empty();
    }

    void delete(@NotNull final String id) {
        facts.remove(id);
    }

}
