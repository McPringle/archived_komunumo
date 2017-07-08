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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
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

import static java.lang.Boolean.TRUE;

@Slf4j
@WebFilter("/*")
public class ClientReloadFilter implements Filter {

    private ServletContext context;

    private Cache<Object, Object> skipRedirectCache;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
        skipRedirectCache = CacheBuilder.newBuilder().maximumSize(10_000).build();
        skipRedirectCache.put("/index.html", TRUE);
        skipRedirectCache.put("/favicon.ico", TRUE);
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {

        final String requestURI = ((HttpServletRequest) request).getRequestURI();
        final boolean isCircle = request.getAttribute("ClientReloadFilter") != null;
        if (isCircle) log.warn("Filter circle detected for URI `{}`!", requestURI);
        if (!isCircle && !skipRedirectOnURI(requestURI)) {
            log.info("Internal redirect from '{}' to '/index.html'.", requestURI);
            request.setAttribute("ClientReloadFilter", TRUE);
            request.getRequestDispatcher("/index.html").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean skipRedirectOnURI(String requestURI) {
        Boolean skipRedirect = (Boolean) skipRedirectCache.getIfPresent(requestURI);
        if (skipRedirect == null) {
            skipRedirect = requestURI.startsWith("/api") ||
                    requestURI.startsWith("/apidocs") ||
                    requestURI.startsWith("/bower_components") ||
                    requestURI.startsWith("/komunumo_components");
            if (!skipRedirect) {
                final String realPath = context.getRealPath(requestURI);
                final File file = realPath == null ? null : new File(realPath);
                skipRedirect = file != null && file.exists();
            }
            skipRedirectCache.put(requestURI, skipRedirect);
        }
        return skipRedirect;
    }

    @Override
    public void destroy() {
    }

}
