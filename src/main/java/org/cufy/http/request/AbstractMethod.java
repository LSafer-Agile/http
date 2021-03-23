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

import org.cufy.http.syntax.HTTPPattern;
import org.cufy.http.syntax.HTTPRegExp;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A basic implementation of the interface {@link Method}.
 *
 * @author LSafer
 * @version 0.0.1
 * @since 0.0.1 ~2021.03.21
 */
public class AbstractMethod implements Method {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 1286045925643725592L;

	/**
	 * The method literal.
	 *
	 * @since 0.0.1 ~2021.03.21
	 */
	@NotNull
	@NonNls
	@Pattern(HTTPRegExp.METHOD)
	protected final String value;

	/**
	 * Construct a new default-implementation method component with its method literal
	 * being the given {@code source}.
	 *
	 * @param source the source of the method literal of the constructed method
	 *               component.
	 * @throws NullPointerException     if the given {@code source} is null.
	 * @throws IllegalArgumentException if the given {@code source} does not match {@link
	 *                                  HTTPRegExp#METHOD}.
	 * @since 0.0.1 ~2021.03.21
	 */
	public AbstractMethod(@NotNull @NonNls @Pattern(HTTPRegExp.METHOD) @Subst("GET") String source) {
		Objects.requireNonNull(source, "source");
		if (!HTTPPattern.METHOD.matcher(source).matches())
			throw new IllegalArgumentException("invalid method: " + source);
		this.value = source;
	}

	@Override
	public boolean equals(@Nullable Object object) {
		if (object == this)
			return true;
		if (object instanceof Method) {
			Method method = (Method) object;

			return Objects.equals(this.value, method.toString());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@NotNull
	@NonNls
	@Pattern(HTTPRegExp.METHOD)
	@Override
	public String toString() {
		return this.value;
	}
}
