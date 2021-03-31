/*
 *	Copyright 2021 Cufy
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package org.cufy.http.request;

import org.cufy.http.syntax.HTTPRegExp;
import org.cufy.http.syntax.URIRegExp;
import org.cufy.http.uri.*;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * <b>Components</b>
 * <br>
 * The request-line; an object describing the first line of an http-request.
 * <br>
 * Components:
 * <ol>
 *     <li>{@link Method}</li>
 *     <li>{@link URI}</li>
 *     <li>{@link HTTPVersion}</li>
 * </ol>
 *
 * @author LSafer
 * @version 0.0.1
 * @since 0.0.1 ~2021.03.20
 */
public interface RequestLine extends Cloneable, Serializable {
	/**
	 * An empty request-line constant.
	 *
	 * @since 0.0.6 ~2021.03.30
	 */
	RequestLine EMPTY = new RawRequestLine();

	/**
	 * <b>Copy</b>
	 * <br>
	 * Construct a new request-line from copying the given {@code requestLine}.
	 *
	 * @param requestLine the request-line to copy.
	 * @return a new copy of the given {@code requestLine}.
	 * @throws NullPointerException if the given {@code requestLine} is null.
	 * @since 0.0.6 ~2021.03.30
	 */
	static RequestLine copy(@NotNull RequestLine requestLine) {
		return new AbstractRequestLine(requestLine);
	}

	/**
	 * <b>Default</b>
	 * <br>
	 * Return a new request-line instance to be a placeholder if a the user has not
	 * specified a request-line.
	 *
	 * @return a new default request-line.
	 * @since 0.0.1 ~2021.03.21
	 */
	static RequestLine defaultRequestLine() {
		return new AbstractRequestLine();
	}

	/**
	 * <b>Empty</b>
	 * <br>
	 * Return an empty unmodifiable request-line.
	 *
	 * @return an empty unmodifiable request-line.
	 * @since 0.0.6 ~2021.03.30
	 */
	static RequestLine empty() {
		return RequestLine.EMPTY;
	}

	/**
	 * <b>Parse</b>
	 * <br>
	 * Construct a new request-line from parsing the given {@code source}.
	 *
	 * @param source the source of the constructed request-line.
	 * @return a new request-line from the given {@code source}.
	 * @throws NullPointerException     if the given {@code source} is null.
	 * @throws IllegalArgumentException if the given {@code source} does not match {@link
	 *                                  HTTPRegExp#REQUEST_LINE}.
	 * @since 0.0.1 ~2021.03.21
	 */
	static RequestLine parse(@NotNull @NonNls @Pattern(HTTPRegExp.REQUEST_LINE) String source) {
		return new AbstractRequestLine(source);
	}

	/**
	 * <b>Raw</b>
	 * <br>
	 * Construct a new raw request-line with the given {@code value}.
	 *
	 * @param value the value of the constructed request-line.
	 * @return a new raw request-line.
	 * @throws NullPointerException if the given {@code value} is null.
	 * @since 0.0.6 ~2021.03.30
	 */
	static RequestLine raw(@NotNull @NonNls String value) {
		return new RawRequestLine(value);
	}

	/**
	 * <b>Unmodifiable</b>
	 * <br>
	 * Construct an unmodifiable copy of the given {@code requestLine}.
	 *
	 * @param requestLine the request-line to be copied.
	 * @return an unmodifiable copy of the given {@code requestLine}.
	 * @throws NullPointerException if the given {@code requestLine} is null.
	 * @since 0.0.6 ~2021.03.30
	 */
	static RequestLine unmodifiable(@NotNull RequestLine requestLine) {
		return new RawRequestLine(requestLine);
	}

	/**
	 * <b>Components</b>
	 * <br>
	 * Construct a new request-line from the given components.
	 *
	 * @param method      the method of the constructed request-line.
	 * @param uri         the uri of the constructed request-line.
	 * @param httpVersion the http-version of the constructed request-line.
	 * @return a new request-line from the given components.
	 * @throws NullPointerException if the given {@code method} or {@code uri} or {@code
	 *                              httpVersion} is null.
	 * @since 0.0.6 ~2021.03.30
	 */
	static RequestLine with(@NotNull Method method, @NotNull URI uri, @NotNull HTTPVersion httpVersion) {
		return new AbstractRequestLine(method, uri, httpVersion);
	}

	/**
	 * Set the authority of this from the given {@code authority}.
	 *
	 * @param authority the authority to be set.
	 * @return this.
	 * @throws NullPointerException          if the given {@code authority} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its authority.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine authority(@NotNull Authority authority) {
		this.uri().authority(authority);
		return this;
	}

	/**
	 * Set the authority of this from the given {@code authority} literal.
	 *
	 * @param authority the authority literal to set the authority of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code authority} is null.
	 * @throws IllegalArgumentException      if the given {@code authority} does not match
	 *                                       {@link URIRegExp#AUTHORITY}.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its authority.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine authority(@NotNull @NonNls @Pattern(URIRegExp.AUTHORITY) String authority) {
		this.uri().authority(authority);
		return this;
	}

	/**
	 * Replace the authority of this to be the result of invoking the given {@code
	 * operator} with the argument being the current authority. If the {@code operator}
	 * returned {@code null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its authority and the given {@code operator}
	 *                                       returned another authority.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine authority(@NotNull UnaryOperator<Authority> operator) {
		Objects.requireNonNull(operator, "operator");
		URI u = this.uri();
		Authority a = u.authority();
		Authority authority = operator.apply(a);

		if (authority != null && authority != a)
			u.authority(authority);

		return this;
	}

	/**
	 * Return the authority defined for this.
	 *
	 * @return the authority of this.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(pure = true)
	default Authority authority() {
		return this.uri().authority();
	}

	/**
	 * Set the fragment of this from the given {@code fragment}.
	 *
	 * @param fragment the fragment to be set.
	 * @return this.
	 * @throws NullPointerException          if the given {@code fragment} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its fragment.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine fragment(@NotNull Fragment fragment) {
		this.uri().fragment(fragment);
		return this;
	}

	/**
	 * Set the fragment of this from the given {@code fragment} literal.
	 *
	 * @param fragment the fragment literal to set the fragment of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code fragment} is null.
	 * @throws IllegalArgumentException      if the given {@code fragment} does not match
	 *                                       {@link URIRegExp#FRAGMENT}.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its fragment.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine fragment(@NotNull @NonNls @Pattern(URIRegExp.FRAGMENT) String fragment) {
		this.uri().fragment(fragment);
		return this;
	}

	/**
	 * Replace the fragment of this to be the result of invoking the given {@code
	 * operator} with the argument being the current fragment. If the {@code operator}
	 * returned {@code null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its fragment and the given {@code operator}
	 *                                       returned another fragment.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine fragment(@NotNull UnaryOperator<Fragment> operator) {
		Objects.requireNonNull(operator, "operator");
		URI u = this.uri();
		Fragment f = u.fragment();
		Fragment fragment = operator.apply(f);

		if (fragment != null && fragment != f)
			u.fragment(fragment);

		return this;
	}

	/**
	 * Return the fragment defined for this.
	 *
	 * @return the fragment of this.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(pure = true)
	default Fragment fragment() {
		return this.uri().fragment();
	}

	/**
	 * Set the host of this to be the given {@code host}.
	 *
	 * @param host the new host of this.
	 * @return this.
	 * @throws NullPointerException          if the given {@code host} is null.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its host.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine host(@NotNull Host host) {
		this.uri().host(host);
		return this;
	}

	/**
	 * Set the host of this from the given {@code host} literal.
	 *
	 * @param host the host literal to set the host of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code host} is null.
	 * @throws IllegalArgumentException      if the given {@code source} does not match
	 *                                       {@link URIRegExp#HOST}.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its host.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine host(@NotNull @NonNls @Pattern(URIRegExp.HOST) String host) {
		this.uri().host(host);
		return this;
	}

	/**
	 * Replace the host of this to be the result of invoking the given {@code operator}
	 * with the argument being the current host. If the {@code operator} returned {@code
	 * null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its host and the given {@code
	 *                                       operator} returned another host.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine host(@NotNull UnaryOperator<Host> operator) {
		Objects.requireNonNull(operator, "operator");
		URI a = this.uri();
		Host h = a.host();
		Host host = operator.apply(h);

		if (host != null && host != h)
			a.host(host);

		return this;
	}

	/**
	 * Return the host defined for this.
	 *
	 * @return the host of this.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@Contract(pure = true)
	default Host host() {
		return this.uri().host();
	}

	/**
	 * Set the http-version of this request-line to be the given {@code httpVersion}.
	 *
	 * @param httpVersion the new http-version of this request-line.
	 * @return this.
	 * @throws NullPointerException          if the given {@code httpVersion} is null.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its http-version.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine httpVersion(@NotNull HTTPVersion httpVersion) {
		throw new UnsupportedOperationException("httpVersion");
	}

	/**
	 * Set the http-version of this from the given {@code httpVersion} literal.
	 *
	 * @param httpVersion the http-version literal to set the http-version of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code httpVersion} is null.
	 * @throws IllegalArgumentException      if the given {@code httpVersion} does not
	 *                                       match {@link HTTPRegExp#HTTP_VERSION}.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its http-version.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine httpVersion(@NotNull @NonNls @Pattern(HTTPRegExp.HTTP_VERSION) String httpVersion) {
		return this.httpVersion(HTTPVersion.parse(httpVersion));
	}

	/**
	 * Replace the http-version of this to be the result of invoking the given {@code
	 * operator} with the argument being the current http-version. If the {@code operator}
	 * returned {@code null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its http-version and the given
	 *                                       {@code operator} returned another
	 *                                       http-version.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine httpVersion(@NotNull UnaryOperator<HTTPVersion> operator) {
		Objects.requireNonNull(operator, "operator");
		HTTPVersion hv = this.httpVersion();
		HTTPVersion httpVersion = operator.apply(hv);

		if (httpVersion != null && httpVersion != hv)
			this.httpVersion(httpVersion);

		return this;
	}

	/**
	 * Set the method of this to the given {@code method}.
	 *
	 * @param method the method to be set.
	 * @return this.
	 * @throws NullPointerException          if the given {@code method} is null.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its method.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine method(@NotNull Method method) {
		throw new UnsupportedOperationException("method");
	}

	/**
	 * Set the method of this from the given {@code method} literal.
	 *
	 * @param method the method literal to set the method of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code method} is null.
	 * @throws IllegalArgumentException      if the given {@code method} does not match
	 *                                       {@link HTTPRegExp#METHOD}.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its method.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine method(@NotNull @NonNls @Pattern(HTTPRegExp.METHOD) String method) {
		return this.method(Method.parse(method));
	}

	/**
	 * Replace the method of this to be the result of invoking the given {@code operator}
	 * with the argument being the current method. If the {@code operator} returned {@code
	 * null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its method and the given {@code
	 *                                       operator} returned another method.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine method(@NotNull UnaryOperator<Method> operator) {
		Objects.requireNonNull(operator, "operator");
		Method m = this.method();
		Method method = operator.apply(m);

		if (method != null && method != m)
			this.method(method);

		return this;
	}

	/**
	 * Set the path of this from the given {@code path}.
	 *
	 * @param path the path to be set.
	 * @return this.
	 * @throws NullPointerException          if the given {@code path} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its path.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine path(@NotNull Path path) {
		this.uri().path(path);
		return this;
	}

	/**
	 * Set the path of this from the given {@code path} literal.
	 *
	 * @param path the path literal to set the path of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code path} is null.
	 * @throws IllegalArgumentException      if the given {@code path} does not match
	 *                                       {@link URIRegExp#PATH}.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its path.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine path(@NotNull @NonNls @Pattern(URIRegExp.PATH) String path) {
		this.uri().path(path);
		return this;
	}

	/**
	 * Replace the path of this to be the result of invoking the given {@code operator}
	 * with the argument being the current path. If the {@code operator} returned {@code
	 * null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its path and the given {@code operator}
	 *                                       returned another path.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine path(@NotNull UnaryOperator<Path> operator) {
		Objects.requireNonNull(operator, "operator");
		URI u = this.uri();
		Path p = u.path();
		Path path = operator.apply(p);

		if (path != null && path != p)
			u.path(path);

		return this;
	}

	/**
	 * Return the path defined for this.
	 *
	 * @return the path of this.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(pure = true)
	default Path path() {
		return this.uri().path();
	}

	/**
	 * Set the port of this to be the given {@code port}.
	 *
	 * @param port the new port of this.
	 * @return this.
	 * @throws NullPointerException          if the given {@code port} is null.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its port.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine port(@NotNull Port port) {
		this.uri().port(port);
		return this;
	}

	/**
	 * Set the port of this from the given {@code port} literal.
	 *
	 * @param port the port literal to set the port of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code port} is null.
	 * @throws IllegalArgumentException      if the given {@code port} does not match
	 *                                       {@link URIRegExp#PORT}.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its port.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine port(@NotNull @NonNls @Pattern(URIRegExp.PORT) String port) {
		this.uri().port(port);
		return this;
	}

	/**
	 * Replace the port of this to be the result of invoking the given {@code operator}
	 * with the argument being the current port. If the {@code operator} returned {@code
	 * null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its port and the given {@code
	 *                                       operator} returned another port.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine port(@NotNull UnaryOperator<Port> operator) {
		Objects.requireNonNull(operator, "operator");
		URI a = this.uri();
		Port p = a.port();
		Port port = operator.apply(p);

		if (port != null && port != p)
			a.port(port);

		return this;
	}

	/**
	 * Return the port defined for this.
	 *
	 * @return the port of this.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@Contract(pure = true)
	default Port port() {
		return this.uri().port();
	}

	/**
	 * Set the query of this from the given {@code query}.
	 *
	 * @param query the query to be set.
	 * @return this.
	 * @throws NullPointerException          if the given {@code query} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its query.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine query(@NotNull Query query) {
		this.uri().query(query);
		return this;
	}

	/**
	 * Set the query of this from the given {@code query} literal.
	 *
	 * @param query the query literal to set the query of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code query} is null.
	 * @throws IllegalArgumentException      if the given {@code query} does not match
	 *                                       {@link URIRegExp#QUERY}.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its query.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine query(@NotNull @NonNls @Pattern(URIRegExp.QUERY) String query) {
		this.uri().query(query);
		return this;
	}

	/**
	 * Replace the query of this to be the result of invoking the given {@code operator}
	 * with the argument being the current query. If the {@code operator} returned {@code
	 * null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its query and the given {@code operator}
	 *                                       returned another query.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine query(@NotNull UnaryOperator<Query> operator) {
		Objects.requireNonNull(operator, "operator");
		URI u = this.uri();
		Query q = u.query();
		Query query = operator.apply(q);

		if (query != null && query != q)
			u.query(query);

		return this;
	}

	/**
	 * Return the query defined for this.
	 *
	 * @return the query of this.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(pure = true)
	default Query query() {
		return this.uri().query();
	}

	/**
	 * Set the scheme of this from the given {@code scheme}.
	 *
	 * @param scheme the scheme to be set.
	 * @return this.
	 * @throws NullPointerException          if the given {@code scheme} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its scheme.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine scheme(@NotNull Scheme scheme) {
		this.uri().scheme(scheme);
		return this;
	}

	/**
	 * Set the scheme of this from the given {@code scheme} literal.
	 *
	 * @param scheme the scheme literal to set the scheme of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code scheme} is null.
	 * @throws IllegalArgumentException      if the given {@code scheme} does not match
	 *                                       {@link URIRegExp#SCHEME}.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its scheme.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine scheme(@NotNull @NonNls @Pattern(URIRegExp.SCHEME) String scheme) {
		this.uri().scheme(scheme);
		return this;
	}

	/**
	 * Replace the scheme of this to be the result of invoking the given {@code operator}
	 * with the argument being the current scheme. If the {@code operator} returned {@code
	 * null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the uri of this does not support changing
	 *                                       its scheme and the given {@code operator}
	 *                                       returned another scheme.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine scheme(@NotNull UnaryOperator<Scheme> operator) {
		Objects.requireNonNull(operator, "operator");
		URI u = this.uri();
		Scheme s = u.scheme();
		Scheme scheme = operator.apply(s);

		if (scheme != null && scheme != s)
			u.scheme(scheme);

		return this;
	}

	/**
	 * Return the scheme defined for this.
	 *
	 * @return the scheme of this.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(pure = true)
	default Scheme scheme() {
		return this.uri().scheme();
	}

	/**
	 * Set the uri of this to the given {@code uri}.
	 *
	 * @param uri the uri to be set.
	 * @return this.
	 * @throws NullPointerException          if the given {@code uri} is null.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its uri.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine uri(@NotNull URI uri) {
		throw new UnsupportedOperationException("uri");
	}

	/**
	 * Set the uri of this from the given {@code uri} literal.
	 *
	 * @param uri the uri literal to set the uri of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code uri} is null.
	 * @throws IllegalArgumentException      if the given {@code uri} does not match
	 *                                       {@link URIRegExp#URI_REFERENCE}.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its uri.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine uri(@NotNull @NonNls @Pattern(URIRegExp.URI_REFERENCE) String uri) {
		return this.uri(URI.parse(uri));
	}

	/**
	 * Replace the uri of this to be the result of invoking the given {@code operator}
	 * with the argument being the current uri. If the {@code operator} returned {@code
	 * null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if this request-line does not support
	 *                                       changing its uri and the given {@code
	 *                                       operator} returned another uri.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine uri(@NotNull UnaryOperator<URI> operator) {
		Objects.requireNonNull(operator, "operator");
		URI u = this.uri();
		URI uri = operator.apply(u);

		if (uri != null && uri != u)
			this.uri(uri);

		return this;
	}

	/**
	 * Set the userinfo of this from the given {@code userinfo}.
	 *
	 * @param userinfo the userinfo to be set.
	 * @return this.
	 * @throws NullPointerException          if the given {@code userinfo} is null.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its userinfo.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine userinfo(@NotNull Userinfo userinfo) {
		this.uri().userinfo(userinfo);
		return this;
	}

	/**
	 * Set the userinfo of this from the given {@code userinfo} literal.
	 *
	 * @param userinfo the userinfo literal to set the userinfo of this from.
	 * @return this.
	 * @throws NullPointerException          if the given {@code userinfo} is null.
	 * @throws IllegalArgumentException      if the given {@code userinfo} does not match
	 *                                       {@link URIRegExp#USERINFO}.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its userinfo.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine userinfo(@NotNull @NonNls @Pattern(URIRegExp.USERINFO) String userinfo) {
		this.uri().userinfo(userinfo);
		return this;
	}

	/**
	 * Replace the userinfo of this to be the result of invoking the given {@code
	 * operator} with the argument being the current userinfo. If the {@code operator}
	 * returned {@code null} then nothing happens.
	 * <br>
	 * Any exceptions thrown by the given {@code operator} will fall throw this method
	 * unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the authority of this does not allow
	 *                                       changing its userinfo and the given {@code
	 *                                       operator} returned another userinfo.
	 * @since 0.0.1 ~2021.03.24
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default RequestLine userinfo(@NotNull UnaryOperator<Userinfo> operator) {
		Objects.requireNonNull(operator, "operator");
		URI a = this.uri();
		Userinfo ui = a.userinfo();
		Userinfo userinfo = operator.apply(ui);

		if (userinfo != null && userinfo != ui)
			a.userinfo(userinfo);

		return this;
	}

	/**
	 * Return the userinfo defined for this.
	 *
	 * @return the userinfo of this.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@Contract(pure = true)
	default Userinfo userinfo() {
		return this.uri().userinfo();
	}

	/**
	 * Capture this request-line into a new object.
	 *
	 * @return a clone of this request-line.
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@Contract(value = "->new", pure = true)
	RequestLine clone();

	/**
	 * Two request-lines are equal when they are the same instance or have an equal {@link
	 * #method()}, {@link #uri()} and {@link #httpVersion()}.
	 *
	 * @param object the object to be checked.
	 * @return if the given {@code object} is a request-line and equals this.
	 * @since 0.0.1 ~2021.03.23
	 */
	@Override
	@Contract(value = "null->false", pure = true)
	boolean equals(@Nullable Object object);

	/**
	 * The hash code of a request is the {@code xor} of the has codes of its components.
	 * (optional)
	 *
	 * @return the hash code of this request-line.
	 * @since 0.0.1 ~2021.03.23
	 */
	@Override
	@Contract(pure = true)
	int hashCode();

	/**
	 * A string representation of this Request-Line. Invoke to get the text representing
	 * this in a request.
	 * <br>
	 * Typically:
	 * <pre>
	 *     Method Request-URI HTTP-Version
	 * </pre>
	 * Example:
	 * <pre>
	 *     GET http://github.com/lsafer HTTP/1.1
	 * </pre>
	 *
	 * @return a string representation of this request-line.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@NonNls
	@Pattern(HTTPRegExp.REQUEST_LINE)
	@Contract(pure = true)
	@Override
	String toString();

	/**
	 * Return the http-version of this request-line.
	 *
	 * @return the http version of this.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@Contract(pure = true)
	HTTPVersion httpVersion();

	/**
	 * Return the method of this request-line.
	 *
	 * @return the method of this.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@Contract(pure = true)
	Method method();

	/**
	 * Return the request-uri of this request-line.
	 *
	 * @return the request-uri of this.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@Contract(pure = true)
	URI uri();
}
//	/**
//	 * Set the port of this from the given {@code port} number.
//	 *
//	 * @param port the port number to set the port of this from.
//	 * @return this.
//	 * @throws IllegalArgumentException      if the given {@code port} is negative.
//	 * @throws UnsupportedOperationException if the authority of this does not allow
//	 *                                       changing its port.
//	 * @since 0.0.1 ~2021.03.24
//	 */
//	@NotNull
//	@Contract(value = "_->this", mutates = "this")
//	default RequestLine port(@Range(from = 0, to = Integer.MAX_VALUE) int port) {
//		this.uri().port(port);
//		return this;
//	}
//	/**
//	 * Set the query of this to the product of combining the given {@code query} array
//	 * with the and-sign "&" as the delimiter. The null elements in the given {@code
//	 * query} array will be skipped.
//	 *
//	 * @param query the values of the new query of this.
//	 * @return this.
//	 * @throws NullPointerException          if the given {@code query} is null.
//	 * @throws IllegalArgumentException      if an element in the given {@code query} does
//	 *                                       not match {@link URIRegExp#ATTR_VALUE}.
//	 * @throws UnsupportedOperationException if the uri of this does not support changing
//	 *                                       its query.
//	 * @since 0.0.1 ~2021.03.24
//	 */
//	@NotNull
//	@Contract(value = "_->this", mutates = "this")
//	default RequestLine query(@NotNull @NonNls @Pattern(URIRegExp.ATTR_VALUE) String @NotNull ... query) {
//		this.uri().query(query);
//		return this;
//	}
//
//	/**
//	 * Set the uri of this from the given {@link java.io.File}.
//	 *
//	 * @param uri the java-file to set the uri of this from.
//	 * @return this.
//	 * @throws NullPointerException          if the given {@code uri} is null.
//	 * @throws SecurityException             If a required system property value cannot be
//	 *                                       accessed.
//	 * @throws UnsupportedOperationException if this request-line does not support
//	 *                                       changing its uri.
//	 * @since 0.0.1 ~2021.03.21
//	 */
//	@NotNull
//	@Contract(value = "_->this", mutates = "this")
//	default RequestLine uri(@NotNull java.io.File uri) {
//		return this.uri(URI.from(uri));
//	}
//
//	/**
//	 * Set the uri of this from the given {@link java.net.URL}.
//	 *
//	 * @param uri the java-url to set the uri of this from.
//	 * @return this.
//	 * @throws NullPointerException          if the given {@code uri} is null.
//	 * @throws IllegalArgumentException      if the URL is not formatted strictly
//	 *                                       according to RFC2396 and cannot be converted
//	 *                                       to a URI.
//	 * @throws UnsupportedOperationException if this request-line does not support
//	 *                                       changing its uri.
//	 * @since 0.0.1 ~2021.03.21
//	 */
//	@NotNull
//	@Contract(value = "_->this", mutates = "this")
//	default RequestLine uri(@NotNull java.net.URL uri) {
//		return this.uri(URI.from(uri));
//	}
//
//	/**
//	 * Set the uri of this from the given {@link java.net.URI}.
//	 *
//	 * @param uri the java-uri to set the uri of this from.
//	 * @return this.
//	 * @throws NullPointerException          if the given {@code uri} is null.
//	 * @throws UnsupportedOperationException if this request-line does not support
//	 *                                       changing its uri.
//	 * @since 0.0.1 ~2021.03.21
//	 */
//	@NotNull
//	@Contract(value = "_->this", mutates = "this")
//	default RequestLine uri(@NotNull java.net.URI uri) {
//		return this.uri(URI.from(uri));
//	}
//
//	/**
//	 * Set the userinfo of this to the product of combining the given {@code userinfo}
//	 * array with the colon ":" as the delimiter. The null elements in the given {@code
//	 * userinfo} array will be treated as empty strings.
//	 *
//	 * @param userinfo the values of the new userinfo of this.
//	 * @return this.
//	 * @throws NullPointerException          if the given {@code userinfo} is null.
//	 * @throws IllegalArgumentException      if an element in the given {@code source}
//	 *                                       does not match {@link URIRegExp#USERINFO} or
//	 *                                       contains a colon ":".
//	 * @throws UnsupportedOperationException if the authority of this does not allow
//	 *                                       changing its userinfo.
//	 * @since 0.0.1 ~2021.03.24
//	 */
//	@NotNull
//	@Contract(value = "_->this", mutates = "this")
//	default RequestLine userinfo(@Nullable @NonNls @Pattern(URIRegExp.USERINFO) String @NotNull ... userinfo) {
//		this.uri().userinfo(userinfo);
//		return this;
//	}
