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
package org.cufy.http.syntax;

import org.intellij.lang.annotations.RegExp;

/**
 * A class containing URI related regular expressions.
 *
 * @author LSafer
 * @version 0.0.1
 * @see <a href="https://tools.ietf.org/html/rfc3986">RFC3986</a>
 * @since 0.0.1 ~2021.03.21
 */
public final class URIRegExp {
	/**
	 * The regex matching numbers between 0 to 255 excluding multiple zeros.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 class dec-octet = DIGIT / %x31-39 DIGIT / "1" 2DIGIT / "2" %x30-34 DIGIT / "25" %x30-35
	public static final String DEC_OCTET = "(?:25[0-5]|(?:2[0-4]|1[0-9]|[1-9])?[0-9])";
	/**
	 * The character class for the delimiters of the generic URI components.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.2">RFC3986 2.2</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//2.2 class gen-delims = ":" / "/" / "?" / "#" / "[" / "]" / "@"
	public static final String GEN_DELIMS = "[:/?#\\[\\]@]";
	/**
	 * A regex matching a 16-bit number in hexadecimal.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 regex h16 = 1*4HEXDIG
	public static final String H16 = "(?:" + ABNFRegExp.HEXDIG + "{1,4})";
	/**
	 * The regex matching an IPv4 address.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 regex IPv4address = dec-octet "." dec-octet "." dec-octet "." dec-octet
	public static final String IPV4ADDRESS =
			"(?:" + URIRegExp.DEC_OCTET + "(?:\\." + URIRegExp.DEC_OCTET + "){3})";
	/**
	 * A regex matching IP-literal.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 regex IP-literal = "[" ( IPv6address / IPvFuture  ) "]"
	public static final String IP_LITERAL =
			"(?:\\[(?:" + URIRegExp.IPV6ADDRESS + "|" + URIRegExp.IPVFUTURE + ")\\])";
	/**
	 * A regex matching the least-significant 32 bits of address.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 regex ls32 = ( h16 ":" h16 ) / IPv4address
	public static final String LS32 =
			"(?:" + URIRegExp.H16 + "\\:" + URIRegExp.H16 + "|" +
			URIRegExp.IPV4ADDRESS + ")";
	/**
	 * The regex matching an IPv6 address.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 regex IPv6address = ...
	public static final String IPV6ADDRESS =
			"(?:" +
			//                           6( h16 ":" ) ls32
			"(?:(?:" + URIRegExp.H16 + "\\:){6}" + URIRegExp.LS32 + ")|" +
			//                      "::" 5( h16 ":" ) ls32
			"(?:\\:\\:" + "(?:" + URIRegExp.H16 + "\\:){5}" + URIRegExp.LS32 + ")|" +
			//[               h16 ] "::" 4( h16 ":" ) ls32
			"(?:" + URIRegExp.H16 + "?\\:\\:" + "(?:" + URIRegExp.H16 + "\\:){4}" +
			URIRegExp.LS32 + ")|" +
			//[ *1( h16 ":" ) h16 ] "::" 3( h16 ":" ) ls32
			"(?:(?:(?:" + URIRegExp.H16 + "\\:)?" + URIRegExp.H16 + ")?\\:\\:" +
			"(?:" + URIRegExp.H16 + "\\:){3}" + URIRegExp.LS32 + ")|" +
			//[ *2( h16 ":" ) h16 ] "::" 2( h16 ":" ) ls32
			"(?:(?:(?:" + URIRegExp.H16 + "\\:){0,2}" + URIRegExp.H16 + ")?\\:\\:" +
			"(?:" + URIRegExp.H16 + "\\:){2}" + URIRegExp.LS32 + ")|" +
			//[ *3( h16 ":" ) h16 ] "::"    h16 ":"   ls32
			"(?:(?:(?:" + URIRegExp.H16 + "\\:){0,3}" + URIRegExp.H16 + ")?\\:\\:" +
			URIRegExp.H16 + "\\:" + URIRegExp.LS32 + ")|" +
			//[ *4( h16 ":" ) h16 ] "::"              ls32
			"(?:(?:(?:" + URIRegExp.H16 + "\\:){0,4}" + URIRegExp.H16 + ")?\\:\\:" +
			URIRegExp.LS32 + ")|" +
			//[ *5( h16 ":" ) h16 ] "::"              h16
			"(?:(?:(?:" + URIRegExp.H16 + "\\:){0,5}" + URIRegExp.H16 + ")?\\:\\:" +
			URIRegExp.H16 + ")|" +
			//[ *6( h16 ":" ) h16 ] "::"
			"(?:(?:(?:" + URIRegExp.H16 + "\\:){0,6}" + URIRegExp.H16 + ")?\\:\\:)" +
			")";
	/**
	 * The percent encoding matching regex.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.1">RFC3986 2.1</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//2.1 regex pct-encoded = "%" HEXDIG HEXDIG
	public static final String PCT_ENCODING = "(?:\\%" + ABNFRegExp.HEXDIG + "{2})";
	/**
	 * The port matching regex.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.3">
	 * 		RFC3986 3.2.3
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.3 regex port = *DIGIT
	public static final String PORT = "(?:" + ABNFRegExp.DIGIT + "*)";
	/**
	 * A regex matching query component.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.4">RFC3986 3.4</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.4 regex query = *(pchar / "/" / "?")
	public static final String QUERY = "(?:(?:" + URIRegExp.P_CHAR + "|\\/|\\?)*)";
	/**
	 * A regex matching relative-part.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-4.2">RFC3986 4.2</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//4.2 regex relative-part = "//" authority path-abempty / path-absolute / path-noscheme / path-empty
	public static final String RELATIVE_PART =
			"(?:" +
			"\\/\\/" + URIRegExp.AUTHORITY + URIRegExp.PATH_ABEMPTY + "|" +
			URIRegExp.PATH_ABSOLUTE + "|" +
			URIRegExp.PATH_NOSCHEME + "|" +
			URIRegExp.PATH_EMPTY +
			")";
	/**
	 * A regex matching relative-ref.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-4.2">RFC3986 4.2</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//4.2 regex relative-ref = relative-part [ "?" query ] [ "#" fragment ]
	public static final String RELATIVE_REF =
			"(?:" + URIRegExp.RELATIVE_PART + "(?:\\?" + URIRegExp.QUERY +
			")?(?:\\#" +
			URIRegExp.FRAGMENT + ")?)";
	/**
	 * The scheme matching regex.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.1">RFC3986 3.1</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.1 regex scheme = ALPHA *(ALPHA / DIGIT / "+" / "-" / ".")
	public static final String SCHEME =
			"(?:" + ABNFRegExp.ALPHA + "(?:" + ABNFRegExp.ALPHA + "|" +
			ABNFRegExp.DIGIT + "|[+-.])*)";
	/**
	 * A regex matching a segment in a path.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex segment = *pchar
	public static final String SEGMENT = "(?:" + URIRegExp.P_CHAR + "*)";
	/**
	 * A regex matching either an empty path or a path that begins with "/".
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex path-abempty = *( "/" segment )
	public static final String PATH_ABEMPTY = "(?:(?:\\/" + URIRegExp.SEGMENT + ")*)";
	/**
	 * A regex matching a non-empty segment in a path.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex segment-nz = 1*pchar
	public static final String SEGMENT_NZ = "(?:" + URIRegExp.P_CHAR + "+)";
	/**
	 * A regex matching rootless-paths.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex path-rootless = segment-nz *( "/" segment )
	public static final String PATH_ROOTLESS =
			"(?:" + URIRegExp.SEGMENT_NZ + "(?:\\/" + URIRegExp.SEGMENT + ")*)";
	/**
	 * A regex matching absolute paths.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex path-absolute = "/" [ segment-nz *( "/" segment ) ]
	public static final String PATH_ABSOLUTE =
			"(?:\\/(?:" + URIRegExp.SEGMENT_NZ + "(?:\\/" + URIRegExp.SEGMENT +
			")*)?)";
	/**
	 * The characters class for the delimiters of the generic URI subcomponents.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.2">RFC3986 2.2</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//2.2 class sub-delims = "!" / "$" / "&" / "'" / "(" / ")" / "*" / "+" / "," / ";" / "="
	public static final String SUB_DELIMS = "[!$&'()*+,;=]";
	/**
	 * The reserved characters class.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.2">RFC3986 2.2</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//2.2 class reserved = gen-delims / sub-delims
	public static final String RESERVED =
			"(?:" + URIRegExp.GEN_DELIMS + "|" + URIRegExp.SUB_DELIMS + ")";
	/**
	 * The always-allowed characters class.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.3">RFC3986 2.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//2.3 class unreserved = ALPHA / DIGIT / "-" / "." / "_" / "~"
	public static final String UNRESERVED =
			"(?:" + ABNFRegExp.ALPHA + "|" + ABNFRegExp.DIGIT + "|[-._~])";
	/**
	 * A custom regex matching the name of a query attribute.
	 *
	 * @since 0.0.1 ~2021.03.22
	 */
	@RegExp
	//3.4 regex x-attr-name = *(<pchar except "&" and "="> / "/" / "?")
	public static final String ATTR_NAME =
			"(?:(?:" + URIRegExp.UNRESERVED + "|" + URIRegExp.PCT_ENCODING +
			"|[!$'()*+,;:@/?])*)";
	/**
	 * A custom regex matching the value of a query attribute.
	 *
	 * @since 0.0.1 ~2021.03.22
	 */
	@RegExp
	//3.4 regex x-attr-value = *(<pchar except "&"> / "/" / "?")
	public static final String ATTR_VALUE =
			"(?:(?:" + URIRegExp.UNRESERVED + "|" + URIRegExp.PCT_ENCODING +
			"|[!$&'()*+,;=:@/?])*)";
	/**
	 * A regex matching non-empty no-colon segment in a path.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex segment-nz-nc = 1*( unreserved / pct-encoded / sub-delims / "@" )
	public static final String SEGMENT_NZ_NC =
			"(?:(?:" + URIRegExp.UNRESERVED + "|" + URIRegExp.PCT_ENCODING + "|" +
			URIRegExp.SUB_DELIMS + "|\\@)+)";
	/**
	 * A regex matching paths with noscheme.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex path-noscheme = segment-nz-nc *( "/" segment )
	public static final String PATH_NOSCHEME =
			"(?:" + URIRegExp.SEGMENT_NZ_NC + "(?:\\/" + URIRegExp.SEGMENT + ")*)";
	/**
	 * A regex matching a valid sequences in a path.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex pchar = unreserved / pct-encoded / sub-delims / ":" / "@"
	public static final String P_CHAR =
			"(?:" + URIRegExp.UNRESERVED + "|" + URIRegExp.PCT_ENCODING + "|" +
			URIRegExp.SUB_DELIMS + "|[:@])";
	/**
	 * A regex matching fragment component.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.5">RFC3986 3.5</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.5 regex fragment = *(pchar / "/" / "?")
	public static final String FRAGMENT = "(?:(?:" + URIRegExp.P_CHAR + "|\\/|\\?)*)";
	/**
	 * A regex matching empty-paths.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex path-empty = 0<pchar>
	public static final String PATH_EMPTY = "(?:" + URIRegExp.P_CHAR + "{0})";
	/**
	 * A regex matching the hierarchy part of a uri.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3">RFC3986 3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3 regex hier-part = "//" authority path-abempty / path-absolute / path-rootless / path-empty
	public static final String HIER_PART =
			"(?:" +
			"\\/\\/" + URIRegExp.AUTHORITY + URIRegExp.PATH_ABEMPTY + "|" +
			URIRegExp.PATH_ABSOLUTE + "|" +
			URIRegExp.PATH_ROOTLESS + "|" +
			URIRegExp.PATH_EMPTY +
			")";
	/**
	 * A regex matching absolute-URI.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-4.3">RFC3986 4.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//4.3 regex absolute-URI = scheme ":" hier-part [ "?" query ]
	public static final String ABSOLUTE_URI =
			"(?:" + URIRegExp.SCHEME + "\\:" + URIRegExp.HIER_PART + "(?:\\?" +
			URIRegExp.QUERY + ")?)";
	/**
	 * A regex matching a path component.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">RFC3986 3.3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.3 regex path = path-abempty / path-absolute / path-noscheme / path-rootless / path-empty
	public static final String PATH = "(?:" +
									  URIRegExp.PATH_ABEMPTY + "|" +
									  URIRegExp.PATH_ABSOLUTE + "|" +
									  URIRegExp.PATH_NOSCHEME + "|" +
									  URIRegExp.PATH_ROOTLESS + "|" +
									  URIRegExp.PATH_EMPTY +
									  ")";
	/**
	 * A regex matching IPvFuture.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 regex IPvFuture = "v" 1*HEXDIG "." 1*(unreserved / sub-delims / ":")
	public static final String IPVFUTURE =
			"(?:[Vv]" + ABNFRegExp.HEXDIG + "+\\.(?:" + URIRegExp.UNRESERVED + "|" +
			URIRegExp.SUB_DELIMS +
			"|\\:)+)";
	/**
	 * A regex matching a registered host name.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 regex reg-name = *( unreserved / pct-encoded / sub-delims )
	public static final String REG_NAME =
			"(?:(?:" + URIRegExp.UNRESERVED + "|" + URIRegExp.PCT_ENCODING + "|" +
			URIRegExp.SUB_DELIMS + ")*)";
	/**
	 * A regex matching the host component.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.2">
	 * 		RFC3986 3.2.2
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.2 regex host = IP-literal / IPv4address / reg-name
	public static final String HOST =
			"(?:" + URIRegExp.IP_LITERAL + "|" + URIRegExp.IPV4ADDRESS + "|" +
			URIRegExp.REG_NAME + ")";
	/**
	 * A regex matching URIs.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3">RFC3986 3</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3 regex URI = scheme ":" hier-part [ "?" query ] [ "#" fragment ]
	public static final String URI =
			"(?:" + URIRegExp.SCHEME + "\\:" + URIRegExp.HIER_PART + "(?:\\?" +
			URIRegExp.QUERY + ")?(?:\\#" +
			URIRegExp.FRAGMENT +
			")?)";
	/**
	 * A regex matching URI-reference.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-4.1">RFC3986 4.1</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//4.1 regex URI-reference = URI / relative-ref
	public static final String URI_REFERENCE =
			"(?:" + URIRegExp.URI + "|" + URIRegExp.RELATIVE_REF + ")";
	/**
	 * The regex matching userinfo component.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2.1">
	 * 		RFC3986 3.2.1
	 * 		</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2.1 regex userinfo = *( unreserved / pct-encoded / sub-delims / ":" )
	public static final String USERINFO =
			"(?:(?:" + URIRegExp.UNRESERVED + "|" + URIRegExp.PCT_ENCODING + "|" +
			URIRegExp.SUB_DELIMS + "|\\:)*)";
	/**
	 * A regex matching the authority component.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2">RFC3986 3.2</a>
	 * @since 0.0.1 ~2021.03.21
	 */
	@RegExp
	//3.2 regex authority = [ userinfo "@" ] host [ ":" port ]
	public static final String AUTHORITY =
			"(?:(?:" + URIRegExp.USERINFO + "\\@)?" + URIRegExp.HOST + "(?:\\:" +
			URIRegExp.PORT + ")?)";

	/**
	 * The regex matching the userinfo subcomponents. (userinfo without a colon)
	 *
	 * @since 0.0.1 ~2021.03.22
	 */
	@RegExp
	//3.2.1 regex x-userinfo-nc = *( unreserved / pct-encoded / sub-delims )
	public static final String USERINFO_NC =
			"(?:(?:" + URIRegExp.UNRESERVED + "|" + URIRegExp.PCT_ENCODING + "|" +
			URIRegExp.SUB_DELIMS + ")*)";

	/**
	 * Utility classes shall have no instances.
	 *
	 * @throws AssertionError when called.
	 * @since 0.0.1 ~2021.03.21
	 */
	private URIRegExp() {
		throw new AssertionError("No instance for you!");
	}
}
