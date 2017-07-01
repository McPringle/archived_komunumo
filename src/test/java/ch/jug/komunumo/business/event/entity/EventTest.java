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
package ch.jug.komunumo.business.event.entity;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

class EventTest {

    private static String TESTDATA_ID = "This is the ID";
    private static String TESTDATA_TITLE = "This is the title";
    private static String TESTDATA_SUBTITLE = "This is the subtitle";
    private static List<String> TESTDATA_TAGS = Lists.newArrayList("foo", "bar");
    private static String TESTDATA_DESCRIPTION = "This is the description";
    private static LocalDateTime TESTDATA_TIME = LocalDateTime.of(2017, 7, 1, 18, 0);
    private static String TESTDATA_CITY = "This is the city";
    private static String TESTDATA_VENUE = "This is the venue";
    private static String TESTDATA_TOSTRING = String.format("Event(id=%s, title=%s, subtitle=%s, tags=%s, description=%s, time=%s, city=%s, venue=%s)",
            TESTDATA_ID, TESTDATA_TITLE, TESTDATA_SUBTITLE, TESTDATA_TAGS, TESTDATA_DESCRIPTION, TESTDATA_TIME, TESTDATA_CITY, TESTDATA_VENUE);

    private static Event event;

    @BeforeAll
    private static void setUp() {
        event = Event.builder()
                .id(TESTDATA_ID)
                .title(TESTDATA_TITLE)
                .subtitle(TESTDATA_SUBTITLE)
                .tags(TESTDATA_TAGS)
                .description(TESTDATA_DESCRIPTION)
                .time(TESTDATA_TIME)
                .city(TESTDATA_CITY)
                .venue(TESTDATA_VENUE)
                .build();
    }

    @AfterAll
    private static void tearDown() {
        event = null;
    }

    @Test
    void getId() {
        assertThat(event.getId(), is(TESTDATA_ID));
    }

    @Test
    void getTitle() {
        assertThat(event.getTitle(), is(TESTDATA_TITLE));
    }

    @Test
    void getSubtitle() {
        assertThat(event.getSubtitle(), is(TESTDATA_SUBTITLE));
    }

    @Test
    void getTags() {
        assertThat(event.getTags(), is(TESTDATA_TAGS));
    }

    @Test
    void getDescription() {
        assertThat(event.getDescription(), is(TESTDATA_DESCRIPTION));
    }

    @Test
    void getTime() {
        assertThat(event.getTime(), is(TESTDATA_TIME));
    }

    @Test
    void getCity() {
        assertThat(event.getCity(), is(TESTDATA_CITY));
    }

    @Test
    void getVenue() {
        assertThat(event.getVenue(), is(TESTDATA_VENUE));
    }

    @Test
    void equals() {
    }

    @Test
    void testHashCode() {
        assertThat(event.hashCode(), is(not(0)));
    }

    @Test
    void testToString() {
        assertThat(event.toString(), is(TESTDATA_TOSTRING));
    }

    @Test
    void builder() {
        assertThat(Event.builder(), is(notNullValue()));
    }

}