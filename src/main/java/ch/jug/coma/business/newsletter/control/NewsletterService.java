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
package ch.jug.coma.business.newsletter.control;

import ch.jug.coma.PersistenceManager;
import ch.jug.coma.business.newsletter.entity.Subscription;
import org.mongodb.morphia.Datastore;

import javax.inject.Singleton;

@Singleton
public class NewsletterService {

    private final Datastore datastore;

    public NewsletterService() {
        this.datastore = PersistenceManager.getDatastore();
    }

    public String subscribe(final Subscription subscription) {
        this.datastore.save(subscription);
        return subscription.getId();
    }

}
