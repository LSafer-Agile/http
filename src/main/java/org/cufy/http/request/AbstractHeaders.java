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

import org.cufy.http.syntax.AbnfPattern;
import org.cufy.http.syntax.HttpPattern;
import org.cufy.http.syntax.HttpRegExp;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * A basic implementation of the interface {@link Headers}.
 *
 * @author LSafer
 * @version 0.0.1
 * @since 0.0.1 ~2021.03.22
 */
public class AbstractHeaders implements Headers {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -3881735342601146869L;

	/**
	 * The headers.
	 *
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	protected Map<@NotNull String, @NotNull String> values;

	/**
	 * <b>Default</b>
	 * <br>
	 * Construct a new default headers.
	 *
	 * @since 0.0.6 ~2021.03.30
	 */
	public AbstractHeaders() {
		this.values = new HashMap<>();
	}

	/**
	 * <b>Copy</b>
	 * <br>
	 * Construct a new headers from copying the given {@code headers}.
	 *
	 * @param headers the headers to copy.
	 * @throws NullPointerException if the given {@code headers} is null.
	 * @since 0.0.6 ~2021.03.30
	 */
	public AbstractHeaders(@NotNull Headers headers) {
		Objects.requireNonNull(headers, "headers");
		this.values = new HashMap<>(headers.values());
	}

	/**
	 * <b>Parse</b>
	 * <br>
	 * Construct a new headers from parsing the given {@code source}.
	 *
	 * @param source the source of the constructed headers.
	 * @throws NullPointerException     if the given {@code source} is null.
	 * @throws IllegalArgumentException if the given {@code source} does not match {@link
	 *                                  HttpRegExp#HEADERS}.
	 * @since 0.0.1 ~2021.03.21
	 */
	public AbstractHeaders(@NotNull @Pattern(HttpRegExp.HEADERS) String source) {
		Objects.requireNonNull(source, "source");
		if (!HttpPattern.HEADERS.matcher(source).matches())
			throw new IllegalArgumentException("invalid headers: " + source);
		//noinspection DynamicRegexReplaceableByCompiledPattern
		this.values = Arrays.stream(AbnfPattern.CRLF.split(source))
							.map(v -> v.split("\\: ?", 2))
							.collect(Collectors.toMap(
									v -> v[0],
									v -> v.length == 2 ? v[1] : "",
									(f, s) -> s,
									HashMap::new
							));
	}

	/**
	 * <b>Components</b>
	 * <br>
	 * Construct a new headers from combining the given {@code values} with the crlf
	 * "\r\n" as the delimiter. The null keys or values in the given {@code source} will
	 * be treated as it does not exist.
	 *
	 * @param values the headers values.
	 * @throws NullPointerException     if the given {@code values} is null.
	 * @throws IllegalArgumentException if a key in the given {@code values} does not
	 *                                  match {@link HttpRegExp#FIELD_NAME}; if a value in
	 *                                  the given {@code values} does not match {@link
	 *                                  HttpRegExp#FIELD_VALUE}.
	 * @since 0.0.6 ~2021.03.30
	 */
	public AbstractHeaders(@NotNull Map<@Nullable String, @Nullable String> values) {
		Objects.requireNonNull(values, "values");
		// noinspection SimplifyStreamApiCallChains
		this.values = StreamSupport.stream(values.entrySet().spliterator(), false)
								   .filter(e -> e != null && e.getKey() != null &&
												e.getValue() != null)
								   .collect(Collectors.toMap(
										   e -> {
											   String name = e.getKey();

											   if (!HttpPattern.FIELD_NAME.matcher(name).matches())
												   throw new IllegalArgumentException(
														   "invalid field name: " + name);

											   return name;
										   },
										   e -> {
											   String value = e.getValue();

											   assert value != null;

											   if (!HttpPattern.FIELD_VALUE.matcher(value).matches())
												   throw new IllegalArgumentException(
														   "invalid field value: " +
														   value);

											   return value;
										   },
										   (f, s) -> s,
										   HashMap::new
								   ));
	}

	@NotNull
	@Override
	public AbstractHeaders clone() {
		try {
			AbstractHeaders clone = (AbstractHeaders) super.clone();
			clone.values = new HashMap<>(this.values);
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	@Override
	public boolean equals(@Nullable Object object) {
		if (object == this)
			return true;
		if (object instanceof Headers) {
			Headers headers = (Headers) object;

			//noinspection ObjectInstantiationInEqualsHashCode
			return Objects.equals(this.values(), headers.values());
		}

		return false;
	}

	@Nullable
	@Pattern(HttpRegExp.FIELD_VALUE)
	@Override
	public String get(@NotNull String name) {
		Objects.requireNonNull(name, "name");
		if (!HttpPattern.FIELD_NAME.matcher(name).matches())
			throw new IllegalArgumentException("invalid field name: " + name);
		return this.values.get(name);
	}

	@Override
	public int hashCode() {
		//noinspection NonFinalFieldReferencedInHashCode
		return this.values.hashCode();
	}

	@NotNull
	@Override
	public Headers put(@NotNull String name, @NotNull String value) {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(value, "value");
		if (!HttpPattern.FIELD_NAME.matcher(name).matches())
			throw new IllegalArgumentException("invalid field name: " + name);
		if (!HttpPattern.FIELD_VALUE.matcher(value).matches())
			throw new IllegalArgumentException("invalid field value: " + value);

		this.values.put(name, value);
		return this;
	}

	@NotNull
	@Override
	public Headers remove(@NotNull String name) {
		Objects.requireNonNull(name, "name");
		if (!HttpPattern.FIELD_NAME.matcher(name).matches())
			throw new IllegalArgumentException("invalid field name: " + name);

		this.values.remove(name);
		return this;
	}

	@NotNull
	@Pattern(HttpRegExp.HEADERS)
	@Override
	public String toString() {
		return this.values.entrySet()
						  .stream()
						  .map(entry -> entry.getKey() + ": " + entry.getValue() + "\r\n")
						  .collect(Collectors.joining());
	}

	@NotNull
	@UnmodifiableView
	@Override
	public Map<@NotNull String, @NotNull String> values() {
		return Collections.unmodifiableMap(this.values);
	}
}
