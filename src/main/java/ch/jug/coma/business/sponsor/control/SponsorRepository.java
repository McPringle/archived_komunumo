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
package ch.jug.coma.business.sponsor.control;

import ch.jug.coma.business.sponsor.entity.Sponsor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

class SponsorRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Sponsor> sponsors = new ConcurrentHashMap<>();

    Sponsor create(@NotNull final Sponsor sponsor) {
        final String id = UUID.randomUUID().toString();
        final long version = sponsor.hashCode();
        final Sponsor sponsorToCreate = sponsor.toBuilder()
                .id(id)
                .version(version)
                .build();
        sponsors.put(id, sponsorToCreate);
        return sponsorToCreate;
    }

    List<Sponsor> readAll() {
        return sponsors.values().stream()
                .sorted(Comparator.comparing(Sponsor::getLevel)
                        .thenComparing(Sponsor::getName))
                .collect(toList());
    }

    Optional<Sponsor> readWithId(@NotNull final String id) {
        return Optional.ofNullable(sponsors.get(id));
    }

    Sponsor update(@NotNull final Sponsor sponsor) {
        final Sponsor previousSponsor = sponsors.getOrDefault(sponsor.getId(), sponsor);
        if (!previousSponsor.getVersion().equals(sponsor.getVersion())) {
            throw new ConcurrentModificationException("You tried to update a sponsor that was modified concurrently!");
        }
        final long version = sponsor.hashCode();
        final Sponsor sponsorToUpdate = sponsor.toBuilder()
                .version(version)
                .build();
        sponsors.put(sponsor.getId(), sponsorToUpdate);
        return sponsorToUpdate;
    }

    void delete(@NotNull final String id) {
        sponsors.remove(id);
    }
}
