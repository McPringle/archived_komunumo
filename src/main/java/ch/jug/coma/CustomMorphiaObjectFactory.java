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
package ch.jug.coma;

import org.mongodb.morphia.mapping.DefaultCreator;
import org.mongodb.morphia.mapping.MappingException;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;

public class CustomMorphiaObjectFactory extends DefaultCreator {

    @Override
    public Object createInstance(final Class clazz) {
        try {
            final Constructor constructor = getNoArgsConstructor(clazz);
            if (constructor != null) {
                return constructor.newInstance();
            }
            try {
                return ReflectionFactory.getReflectionFactory()
                        .newConstructorForSerialization(clazz, Object.class.getDeclaredConstructor(null))
                        .newInstance(null);
            } catch (final Exception e) {
                throw new MappingException("Failed to instantiate " + clazz.getName(), e);
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    private Constructor getNoArgsConstructor(final Class clazz) {
        try {
            final Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor;
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }

}
