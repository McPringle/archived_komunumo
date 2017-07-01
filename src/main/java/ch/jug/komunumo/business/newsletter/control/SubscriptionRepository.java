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
package ch.jug.komunumo.business.newsletter.control;

import ch.jug.komunumo.business.newsletter.entity.Subscription;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

class SubscriptionRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();

    Subscription create(@NotNull final Subscription subscription) {
        final String id = UUID.randomUUID().toString();
        final long version = subscription.hashCode();
        final Subscription subscriptionToCreate = subscription.toBuilder()
                .id(id)
                .version(version)
                .build();
        subscriptions.put(id, subscriptionToCreate);
        return subscriptionToCreate;

    }

    List<Subscription> readAll() {
        return subscriptions.values().stream()
                .sorted(Comparator.comparing(Subscription::getEmail))
                .collect(toList());
    }

}
