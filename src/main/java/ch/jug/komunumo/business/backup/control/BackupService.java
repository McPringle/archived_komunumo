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
package ch.jug.komunumo.business.backup.control;

import ch.jug.komunumo.business.backup.entity.BackupData;
import ch.jug.komunumo.business.event.control.EventService;
import ch.jug.komunumo.business.newsletter.control.NewsletterService;
import ch.jug.komunumo.business.sigs.control.SIGService;
import ch.jug.komunumo.business.sponsor.control.SponsorService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class BackupService {

    private Map<String, BackupData> services = new HashMap<>();

    @Inject
    public BackupService(final EventService eventService,
                         final NewsletterService newsletterService,
                         final SIGService sigService,
                         final SponsorService sponsorService) {
        this.services.put("event", eventService);
        this.services.put("newsletter", newsletterService);
        this.services.put("sig", sigService);
        this.services.put("sponsor", sponsorService);
    }

    public Map<String, List<Serializable>> createBackup() {
        final Map<String, List<Serializable>> data = new HashMap<>();
        for (final String key : services.keySet()) {
            final BackupData service = services.get(key);
            data.put(key, service.backup());
        }
        return data;
    }

}
