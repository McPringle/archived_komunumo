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

import ch.jug.coma.PersistenceManager;
import ch.jug.coma.business.sponsor.entity.Level;
import ch.jug.coma.business.sponsor.entity.Sponsor;
import org.mongodb.morphia.Datastore;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class SponsorService {

    private Comparator<Sponsor> sortByLevel = (s1, s2) -> s1.getLevel().compareTo(s2.getLevel());
    private Comparator<Sponsor> sortByName = (s1, s2) -> s1.getName().toLowerCase().compareTo(s2.getName().toLowerCase());

    private final Datastore datastore;

    public SponsorService() {
        this.datastore = PersistenceManager.getDatastore();
    }

    public String createSponsor(final Sponsor sponsor) {
        this.datastore.save(sponsor);
        return sponsor.getId();
    }

    public List<Sponsor> readAllSponsors() {
        return this.datastore.createQuery(Sponsor.class).asList().stream()
                .sorted(sortByLevel
                        .thenComparing(sortByName))
                .collect(Collectors.toList());
    }

    public List<Sponsor> readActiveSponsors() {
        return readAllSponsors().stream()
                .filter(Sponsor::getActive)
                .collect(Collectors.toList());
    }

    public List<Sponsor> readSponsorsWithLevel(final Level level) {
        return readAllSponsors().stream()
                .filter(s -> s.getLevel().equals(level))
                .sorted(sortByName)
                .collect(Collectors.toList());
    }

    public List<Sponsor> readActiveSponsorsWithLevel(final Level level) {
        return readSponsorsWithLevel(level).stream()
                .filter(Sponsor::getActive)
                .collect(Collectors.toList());
    }

}
