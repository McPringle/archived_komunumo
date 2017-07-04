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
package ch.jug.komunumo;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Slf4j
@WebFilter("/*")
public class ClientReloadFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {

        final String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (!requestURI.startsWith("/api") &&
                !requestURI.equals("/") &&
                !requestURI.equals("/index.html") &&
                !isStaticFile(requestURI)) {
            log.info("Internal redirect from '{}' to '/index.html'.", requestURI);
            request.getRequestDispatcher("/index.html").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isStaticFile(String requestURI) {
        final String realPath = context.getRealPath(requestURI);
        final File file = realPath == null ? null : new File(realPath);
        return file != null && file.exists();
    }

    @Override
    public void destroy() {
    }

}
