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
import ch.jug.coma.business.event.entity.Event;
import ch.jug.coma.business.newsletter.entity.Subscription;
import pl.setblack.airomem.core.PersistenceController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;

@Singleton
public class NewsletterService {

    private PersistenceController<SubscriptionRepository> controller;

    @PostConstruct
    public void setupResources() {
        controller = PersistenceManager.createController(Subscription.class, SubscriptionRepository::new);
    }

    @PreDestroy
    public void cleanupResources() {
        controller.close();
    }

    public Subscription create(final Subscription subscription) {
        return controller.executeAndQuery(mgr -> mgr.create(subscription));
    }

}
