/**
 * SportChef – Sports Competition Management Software
 * Copyright (C) 2015 Marcus Fihlon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/ <http://www.gnu.org/licenses/>>.
 */
package ch.sportchef.business.user.boundary;

import ch.sportchef.business.user.entity.User;

import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Stateless
public class UserManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    private final AtomicLong userSeq = new AtomicLong(1);

    public User create(@NotNull final User user) {
        final long userId = userSeq.incrementAndGet();
        user.setUserId(userId);
        this.users.put(userId, user);
        return user;
    }

    public User update(@NotNull final User user) {
        this.users.put(user.getUserId(), user);
        return user;
    }

    public User findByUserId(final long userId) {
        return this.users.get(userId);
    }

    public List<User> findAll() {
        return this.users.values().stream().collect(Collectors.toList());
    }

    public void delete(final long userId) {
        this.users.remove(userId);
    }
}
