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
package ch.jug.komunumo.business.sponsor.entity;

public enum Level {

    PLATIN("platin"),
    GOLD("gold"),
    SILVER("silver");

    private final String value;

    Level(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Level fromString(final String value) {
        for (final Level level : Level.values()) {
            if (level.value.equalsIgnoreCase(value)) {
                return level;
            }
        }
        return null;
    }

}
