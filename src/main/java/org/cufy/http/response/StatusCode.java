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
package org.cufy.http.response;

import org.cufy.http.syntax.HTTPRegExp;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.Serializable;

/**
 * <b>Constant</b> (No Encode)
 * <br>
 * The "Status-Code" part of the "Status-Line" part of a response.
 *
 * @author LSafer
 * @version 0.0.1
 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes">
 * 		List of HTTP status codes
 * 		</a>
 * @since 0.0.1 ~2021.03.23
 */
public interface StatusCode extends Serializable {
	/**
	 * The request has been accepted for processing, but the processing has not been
	 * completed. The request might or might not be eventually acted upon, and may be
	 * disallowed when processing occurs.
	 *
	 * @see <a href="http://httpstatus.es/202">httpstatus/202</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode ACCEPTED = new AbstractStatusCode(202);
	/**
	 * The members of a DAV binding have already been enumerated in a preceding part of
	 * the (multi-status) response, and are not being included again.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc5842">RFC5842</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode ALREADY_REPORTED = new AbstractStatusCode(208);
	/**
	 * The server was acting as a gateway or proxy and received an invalid response from
	 * the upstream server.
	 *
	 * @see <a href="http://httpstatus.es/502">httpstatus/502</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode BAD_GATEWAY = new AbstractStatusCode(502);
	/**
	 * The server cannot or will not process the request due to an apparent client error
	 * (e.g., malformed request syntax, size too large, invalid request message framing,
	 * or deceptive request routing).
	 *
	 * @see <a href="http://httpstatus.es/400">httpstatus/400</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode BAD_REQUEST = new AbstractStatusCode(400);
	/**
	 * Indicates that the request could not be processed because of conflict in the
	 * current state of the resource, such as an edit conflict between multiple
	 * simultaneous updates.
	 *
	 * @see <a href="http://httpstatus.es/409">httpstatus/409</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode CONFLICT = new AbstractStatusCode(409);
	/**
	 * The server has received the request headers and the client should proceed to send
	 * the request body (in the case of a request for which a body needs to be sent; for
	 * example, a POST request). Sending a large request body to a server after a request
	 * has been rejected for inappropriate headers would be inefficient. To have a server
	 * check the request's headers, a client must send Expect: 100-continue as a header in
	 * its initial request and receive a 100 Continue status code in response before
	 * sending the body. If the client receives an error code such as 403 (Forbidden) or
	 * 405 (Method Not Allowed) then it should not send the request's body. The response
	 * 417 Expectation Failed indicates that the request should be repeated without the
	 * Expect header as it indicates that the server does not support expectations (this
	 * is the case, for example, of HTTP/1.0 servers).
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.1.1">RFC7231-5.1.1</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode CONTINUE = new AbstractStatusCode(100);
	/**
	 * The request has been fulfilled, resulting in the creation of a new resource.
	 *
	 * @see <a href="http://httpstatus.es/201">httpstatus/201</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode CREATED = new AbstractStatusCode(201);
	/**
	 * Used to return some response headers before final HTTP message.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc8297">RFC8297</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode EARLY_HINTS = new AbstractStatusCode(103);
	/**
	 * An empty status code constant.
	 *
	 * @since 0.0.6 ~2021.03.30
	 */
	StatusCode EMPTY = new RawStatusCode();
	/**
	 * The server cannot meet the requirements of the Expect request-header field.
	 *
	 * @see <a href="http://httpstatus.es/417">httpstatus/417</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode EXPECTATION_FAILED = new AbstractStatusCode(417);
	/**
	 * The request failed because it depended on another request and that request failed
	 * (e.g., a PROPPATCH).
	 *
	 * @see <a href="http://httpstatus.es/424">httpstatus/424</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode FAILED_DEPENDENCY = new AbstractStatusCode(424);
	/**
	 * The request contained valid data and was understood by the server, but the server
	 * is refusing action. This may be due to the user not having the necessary
	 * permissions for a resource or needing an account of some sort, or attempting a
	 * prohibited action (e.g. creating a duplicate record where only one is allowed).
	 * This code is also typically used if the request provided authentication by
	 * answering the WWW-Authenticate header field challenge, but the server did not
	 * accept that authentication. The request should not be repeated.
	 *
	 * @see <a href="http://httpstatus.es/403">httpstatus/403</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode FORBIDDEN = new AbstractStatusCode(403);
	/**
	 * Tells the client to look at (browse to) another URL. 302 has been superseded by 303
	 * and 307. This is an example of industry practice contradicting the standard. The
	 * HTTP/1.0 specification (RFC 1945) required the client to perform a temporary
	 * redirect (the original describing phrase was "Moved Temporarily"), but popular
	 * browsers implemented 302 with the functionality of a 303 See Other. Therefore,
	 * HTTP/1.1 added status codes 303 and 307 to distinguish between the two behaviours.
	 * However, some Web applications and frameworks use the 302 status code as if it were
	 * the 303.
	 *
	 * @see <a href="http://httpstatus.es/302">httpstatus/302</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode FOUND = new AbstractStatusCode(302);
	/**
	 * The server was acting as a gateway or proxy and did not receive a timely response
	 * from the upstream server.
	 *
	 * @see <a href="http://httpstatus.es/504">httpstatus/504</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode GATEWAY_TIMEOUT = new AbstractStatusCode(504);
	/**
	 * Indicates that the resource requested is no longer available and will not be
	 * available again. This should be used when a resource has been intentionally removed
	 * and the resource should be purged. Upon receiving a 410 status code, the client
	 * should not request the resource in the future. Clients such as search engines
	 * should remove the resource from their indices.[42] Most use cases do not require
	 * clients and search engines to purge the resource, and a "404 Not Found" may be used
	 * instead.
	 *
	 * @see <a href="http://httpstatus.es/410">httpstatus/410</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode GONE = new AbstractStatusCode(410);
	/**
	 * The server does not support the HTTP protocol version used in the request.
	 *
	 * @see <a href="http://httpstatus.es/505">httpstatus/505</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode HTTP_VERSION_NOT_SUPPORTED = new AbstractStatusCode(505);
	/**
	 * The server has fulfilled a request for the resource, and the response is a
	 * representation of the result of one or more instance-manipulations applied to the
	 * current instance.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc3229">RFC3229</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode IM_USED = new AbstractStatusCode(226);
	/**
	 * The server is unable to store the representation needed to complete the request.
	 *
	 * @see <a href="http://httpstatus.es/507">httpstatus/507</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode INSUFFICIENT_STORAGE = new AbstractStatusCode(507);
	/**
	 * A generic error message, given when an unexpected condition was encountered and no
	 * more specific message is suitable.
	 *
	 * @see <a href="http://httpstatus.es/500">httpstatus/500</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode INTERNAL_SERVER_ERROR = new AbstractStatusCode(500);
	/**
	 * This code was defined in 1998 as one of the traditional IETF April Fools' jokes, in
	 * RFC 2324, Hyper Text Coffee Pot Control Protocol, and is not expected to be
	 * implemented by actual HTTP servers. The RFC specifies this code should be returned
	 * by teapots requested to brew coffee.[53] This HTTP status is used as an Easter egg
	 * in some websites, such as Google.com's I'm a teapot easter egg.
	 *
	 * @see <a href="http://httpstatus.es/418">httpstatus/418</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode I_AM_A_TEAPOT = new AbstractStatusCode(418);
	/**
	 * The request did not specify the length of its content, which is required by the
	 * requested resource.
	 *
	 * @see <a href="http://httpstatus.es/411">httpstatus/411</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode LENGTH_REQUIRED = new AbstractStatusCode(411);
	/**
	 * The resource that is being accessed is locked.
	 *
	 * @see <a href="http://httpstatus.es/423">httpstatus/423</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode LOCKED = new AbstractStatusCode(423);
	/**
	 * The server detected an infinite loop while processing the request (sent instead of
	 * 208 Already Reported).
	 *
	 * @see <a href="http://httpstatus.es/508">httpstatus/508</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode LOOP_DETECTED = new AbstractStatusCode(508);
	/**
	 * A request method is not supported for the requested resource; for example, a GET
	 * request on a form that requires data to be presented via POST, or a PUT request on
	 * a read-only resource.
	 *
	 * @see <a href="http://httpstatus.es/405">httpstatus/405</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode METHOD_NOT_ALLOWED = new AbstractStatusCode(405);
	/**
	 * The request was directed at a server that is not able to produce a response (for
	 * example because of connection reuse).
	 *
	 * @see <a href="http://httpstatus.es/421">httpstatus/421</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode MISDIRECTED_REQUEST = new AbstractStatusCode(421);
	/**
	 * This and all future requests should be directed to the given URI.
	 *
	 * @see <a href="http://httpstatus.es/301">httpstatus/301</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode MOVED_PERMANENTLY = new AbstractStatusCode(301);
	/**
	 * Indicates multiple options for the resource from which the client may choose (via
	 * agent-driven content negotiation). For example, this code could be used to present
	 * multiple video format options, to list files with different filename extensions, or
	 * to suggest word-sense disambiguation.
	 *
	 * @see <a href="http://httpstatus.es/300">httpstatus/300</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode MULTIPLE_CHOICES = new AbstractStatusCode(300);
	/**
	 * The message body that follows is by default an XML message and can contain a number
	 * of separate response codes, depending on how many sub-requests were made.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc4918">RFC4918</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode MULTI_STATUS = new AbstractStatusCode(207);
	/**
	 * The client needs to authenticate to gain network access. Intended for use by
	 * intercepting proxies used to control access to the network (e.g., "captive portals"
	 * used to require agreement to Terms of Service before granting full Internet access
	 * via a Wi-Fi hotspot).
	 *
	 * @see <a href="http://httpstatus.es/511">httpstatus/511</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode NETWORK_AUTHENTICATION_REQUIRED = new AbstractStatusCode(511);
	/**
	 * The server is a transforming proxy (e.g. a Web accelerator) that received a 200 OK
	 * from its origin, but is returning a modified version of the origin's response.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.4">RFC7231-6.3.4</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode NON_AUTHORITATIVE_INFORMATION = new AbstractStatusCode(203);
	/**
	 * The requested resource is capable of generating only content not acceptable
	 * according to the Accept headers sent in the request. See Content negotiation.
	 *
	 * @see <a href="http://httpstatus.es/406">httpstatus/406</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode NOT_ACCEPTABLE = new AbstractStatusCode(406);
	/**
	 * Further extensions to the request are required for the server to fulfil it.
	 *
	 * @see <a href="http://httpstatus.es/510">httpstatus/510</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode NOT_EXTENDED = new AbstractStatusCode(510);
	/**
	 * The requested resource could not be found but may be available in the future.
	 * Subsequent requests by the client are permissible.
	 *
	 * @see <a href="http://httpstatus.es/404">httpstatus/404</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode NOT_FOUND = new AbstractStatusCode(404);
	/**
	 * The server either does not recognize the request method, or it lacks the ability to
	 * fulfil the request. Usually this implies future availability (e.g., a new feature
	 * of a web-service API).
	 *
	 * @see <a href="http://httpstatus.es/501">httpstatus/501</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode NOT_IMPLEMENTED = new AbstractStatusCode(501);
	/**
	 * Indicates that the resource has not been modified since the version specified by
	 * the request headers If-Modified-Since or If-None-Match. In such case, there is no
	 * need to retransmit the resource since the client still has a previously-downloaded
	 * copy.
	 *
	 * @see <a href="http://httpstatus.es/304">httpstatus/304</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode NOT_MODIFIED = new AbstractStatusCode(304);
	/**
	 * The server successfully processed the request, and is not returning any content.
	 *
	 * @see <a href="http://httpstatus.es/204">httpstatus/204</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode NO_CONTENT = new AbstractStatusCode(204);
	/**
	 * Standard response for successful HTTP requests. The actual response will depend on
	 * the request method used. In a GET request, the response will contain an entity
	 * corresponding to the requested resource. In a POST request, the response will
	 * contain an entity describing or containing the result of the action.
	 *
	 * @since <a href="https://tools.ietf.org/html/rfc2616#section-10.2.1">RFC2616-10.2.1</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode OK = new AbstractStatusCode(200);
	/**
	 * The server is delivering only part of the resource (byte serving) due to a range
	 * header sent by the client. The range header is used by HTTP clients to enable
	 * resuming of interrupted downloads, or split a download into multiple simultaneous
	 * streams.
	 *
	 * @see <a href="http://httpstatus.es/206">httpstatus/206</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode PARTIAL_CONTENT = new AbstractStatusCode(206);
	/**
	 * The request is larger than the server is willing or able to process. Previously
	 * called "Request Entity Too Large".
	 *
	 * @see <a href="http://httpstatus.es/413">httpstatus/413</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode PAYLOAD_TOO_LARGE = new AbstractStatusCode(413);
	/**
	 * Reserved for future use. The original intention was that this code might be used as
	 * part of some form of digital cash or micro-payment scheme, as proposed, for
	 * example, by GNU Taler, but that has not yet happened, and this code is not widely
	 * used. Google Developers API uses this status if a particular developer has exceeded
	 * the daily limit on requests. Sipgate uses this code if an account does not have
	 * sufficient funds to start a call.[36] Shopify uses this code when the store has not
	 * paid their fees and is temporarily disabled. Stripe uses this code for failed
	 * payments where parameters were correct, for example blocked fraudulent payments.
	 *
	 * @see <a href="http://httpstatus.es/402">httpstatus/402</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode PAYMENT_REQUIRED = new AbstractStatusCode(402);
	/**
	 * The request and all future requests should be repeated using another URI. 307 and
	 * 308 parallel the behaviors of 302 and 301, but do not allow the HTTP method to
	 * change. So, for example, submitting a form to a permanently redirected resource may
	 * continue smoothly.
	 *
	 * @see <a href="http://httpstatus.es/308">httpstatus/308</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode PERMANENT_REDIRECT = new AbstractStatusCode(308);
	/**
	 * The server does not meet one of the preconditions that the requester put on the
	 * request header fields.
	 *
	 * @see <a href="http://httpstatus.es/412">httpstatus/412</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode PRECONDITION_FAILED = new AbstractStatusCode(412);
	/**
	 * The origin server requires the request to be conditional. Intended to prevent the
	 * 'lost update' problem, where a client GETs a resource's state, modifies it, and
	 * PUTs it back to the server, when meanwhile a third party has modified the state on
	 * the server, leading to a conflict.
	 *
	 * @see <a href="http://httpstatus.es/428">httpstatus/428</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode PRECONDITION_REQUIRED = new AbstractStatusCode(428);
	/**
	 * A WebDAV request may contain many sub-requests involving file operations, requiring
	 * a long time to complete the request. This code indicates that the server has
	 * received and is processing the request, but no response is available yet. This
	 * prevents the client from timing out and assuming the request was lost.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc2518">RFC2518</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode PROCESSING = new AbstractStatusCode(102);
	/**
	 * The client must first authenticate itself with the proxy.
	 *
	 * @see <a href="http://httpstatus.es/407">httpstatus/407</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode PROXY_AUTHENTICATION_REQUIRED = new AbstractStatusCode(407);
	/**
	 * The client has asked for a portion of the file (byte serving), but the server
	 * cannot supply that portion. For example, if the client asked for a part of the file
	 * that lies beyond the end of the file.[50] Called "Requested Range Not Satisfiable"
	 * previously.
	 *
	 * @see <a href="http://httpstatus.es/416">httpstatus/416</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode RANGE_NOT_SATISFIABLE = new AbstractStatusCode(416);
	/**
	 * The server is unwilling to process the request because either an individual header
	 * field, or all the header fields collectively, are too large.
	 *
	 * @see <a href="http://httpstatus.es/431">httpstatus/431</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode REQUEST_HEADER_FIELDS_TOO_LARGE = new AbstractStatusCode(431);
	/**
	 * The server timed out waiting for the request. According to HTTP specifications:
	 * "The client did not produce a request within the time that the server was prepared
	 * to wait. The client MAY repeat the request without modifications at any later
	 * time.
	 *
	 * @see <a href="http://httpstatus.es/408">httpstatus/408</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode REQUEST_TIMEOUT = new AbstractStatusCode(408);
	/**
	 * The server successfully processed the request, asks that the requester reset its
	 * document view, and is not returning any content.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.6">RFC7231-6.3.6</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode RESET_CONTENT = new AbstractStatusCode(205);
	/**
	 * The response to the request can be found under another URI using the GET method.
	 * When received in response to a POST (or PUT/DELETE), the client should presume that
	 * the server has received the data and should issue a new GET request to the given
	 * URI.
	 *
	 * @see <a href="http://httpstatus.es/303">httpstatus/303</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode SEE_OTHER = new AbstractStatusCode(303);
	/**
	 * The server cannot handle the request (because it is overloaded or down for
	 * maintenance). Generally, this is a temporary state.
	 *
	 * @see <a href="http://httpstatus.es/503">httpstatus/503</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode SERVICE_UNAVAILABLE = new AbstractStatusCode(503);
	/**
	 * The requester has asked the server to switch protocols and the server has agreed to
	 * do so.
	 *
	 * @see <a href="https://httpstatuses.com/101">httpstatuses/101</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode SWITCH_PROTOCOLS = new AbstractStatusCode(101);
	/**
	 * No longer used. Originally meant "Subsequent requests should use the specified
	 * proxy.
	 *
	 * @see <a href="http://httpstatus.es/306">httpstatus/306</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode SWITCH_PROXY = new AbstractStatusCode(306);
	/**
	 * In this case, the request should be repeated with another URI; however, future
	 * requests should still use the original URI. In contrast to how 302 was historically
	 * implemented, the request method is not allowed to be changed when reissuing the
	 * original request. For example, a POST request should be repeated using another POST
	 * request.
	 *
	 * @see <a href="http://httpstatus.es/307">httpstatus/307</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode TEMPORARY_REDIRECT = new AbstractStatusCode(307);
	/**
	 * Indicates that the server is unwilling to risk processing a request that might be
	 * replayed.
	 *
	 * @see <a href="http://httpstatus.es/425">httpstatus/425</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode TOO_EARLY = new AbstractStatusCode(425);
	/**
	 * The user has sent too many requests in a given amount of time. Intended for use
	 * with rate-limiting schemes.
	 *
	 * @see <a href="http://httpstatus.es/429">httpstatus/429</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode TOO_MANY_REQUESTS = new AbstractStatusCode(429);
	/**
	 * Similar to 403 Forbidden, but specifically for use when authentication is required
	 * and has failed or has not yet been provided. The response must include a
	 * WWW-Authenticate header field containing a challenge applicable to the requested
	 * resource. See Basic access authentication and Digest access authentication. 401
	 * semantically means "unauthorised", the user does not have valid authentication
	 * credentials for the target resource.
	 * <br>
	 * Note: Some sites incorrectly issue HTTP 401 when an IP address is banned from the
	 * website (usually the website domain) and that specific address is refused
	 * permission to access a website.
	 *
	 * @see <a href="http://httpstatus.es/401">httpstatus/401</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode UNAUTHORIZED = new AbstractStatusCode(401);
	/**
	 * A server operator has received a legal demand to deny access to a resource or to a
	 * set of resources that includes the requested resource.[60] The code 451 was chosen
	 * as a reference to the novel Fahrenheit 451 (see the Acknowledgements in the RFC).
	 *
	 * @see <a href="http://httpstatus.es/451">httpstatus/451</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode UNAVAILABLE_FOR_LEGAL_REASONS = new AbstractStatusCode(451);
	/**
	 * The request was well-formed but was unable to be followed due to semantic errors.
	 *
	 * @see <a href="http://httpstatus.es/422">httpstatus/422</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode UNPROCESSABLE_ENTITY = new AbstractStatusCode(422);
	/**
	 * The request entity has a media type which the server or resource does not support.
	 * For example, the client uploads an image as image/svg+xml, but the server requires
	 * that images use a different format.
	 *
	 * @see <a href="http://httpstatus.es/415">httpstatus/415</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode UNSUPPORTED_MEDIA_TYPE = new AbstractStatusCode(415);
	/**
	 * The client should switch to a different protocol such as TLS/1.3, given in the
	 * Upgrade header field.
	 *
	 * @see <a href="http://httpstatus.es/426">httpstatus/426</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode UPGRADE_REQUIRED = new AbstractStatusCode(426);
	/**
	 * The URI provided was too long for the server to process. Often the result of too
	 * much data being encoded as a query-string of a GET request, in which case it should
	 * be converted to a POST request. Called "Request-URI Too Long" previously.
	 *
	 * @see <a href="http://httpstatus.es/414">httpstatus/414</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode URI_TOO_LONG = new AbstractStatusCode(414);
	/**
	 * The requested resource is available only through a proxy, the address for which is
	 * provided in the response. For security reasons, many HTTP clients (such as Mozilla
	 * Firefox and Internet Explorer) do not obey this status code.
	 *
	 * @see <a href="http://httpstatus.es/305">httpstatus/305</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode USE_PROXY = new AbstractStatusCode(305);
	/**
	 * Transparent content negotiation for the request results in a circular reference.
	 *
	 * @see <a href="http://httpstatus.es/506">httpstatus/506</a>
	 * @since 0.0.1 ~2021.03.23
	 */
	StatusCode VARIANT_ALSO_NEGOTIATES = new AbstractStatusCode(506);

	/**
	 * <b>Raw</b>
	 * <br>
	 * Construct a new raw status-code with the given {@code value}.
	 *
	 * @param value the value of the constructed status-code.
	 * @return a new raw status-code.
	 * @throws NullPointerException if the given {@code value} is null.
	 * @since 0.0.6 ~2021.03.30
	 */
	static StatusCode raw(@NotNull String value) {
		return new RawStatusCode(value);
	}

	/**
	 * <b>Default</b>
	 * <br>
	 * Return a status-code instance to be a placeholder if a the user has not specified a
	 * status-code.
	 *
	 * @return the default status-code.
	 * @since 0.0.1 ~2021.03.20
	 */
	static StatusCode statusCode() {
		return StatusCode.OK;
	}

	/**
	 * <b>Integration</b>
	 * <br>
	 * Create a new status-code from the given status {@code number}.
	 *
	 * @param number the status number
	 * @return a new status-code from the given status {@code number}.
	 * @throws IllegalArgumentException if the given {@code number} is negative.
	 * @since 0.0.1 ~2021.03.23
	 */
	static StatusCode statusCode(@Range(from = 0, to = 999) int number) {
		return new AbstractStatusCode(number);
	}

	/**
	 * <b>Parse</b>
	 * <br>
	 * Create a new status-code from parsing the given {@code source}.
	 *
	 * @param source the status-code sequence to be parsed into a new status-code.
	 * @return a status-code from parsing the given {@code source}.
	 * @throws NullPointerException     if the given {@code source} is null.
	 * @throws IllegalArgumentException if the given {@code source} does not match {@link
	 *                                  HTTPRegExp#STATUS_CODE}.
	 * @since 0.0.1 ~2021.03.20
	 */
	static StatusCode statusCode(@NotNull @Pattern(HTTPRegExp.STATUS_CODE) String source) {
		return new AbstractStatusCode(source);
	}

	/**
	 * Two status-codes are equal when they are the same instance or have the same {@code
	 * status-code-literal} (the value returned from {@link #toString()}).
	 *
	 * @param object the object to be checked.
	 * @return if the given {@code object} is a status-code and equals this.
	 * @since 0.0.1 ~2021.03.23
	 */
	@Override
	@Contract(value = "null->false", pure = true)
	boolean equals(@Nullable Object object);

	/**
	 * The hash code of a status-code is the hash code of its status-code-literal.
	 * (optional)
	 *
	 * @return the hash code of this status-code.
	 * @since 0.0.1 ~2021.03.23
	 */
	@Override
	@Contract(pure = true)
	int hashCode();

	/**
	 * A string representation of the StatusCode. Invoke to get the text representing this
	 * in a response.
	 * <br>
	 * Example:
	 * <pre>
	 *     200
	 * </pre>
	 *
	 * @return a string representation of this StatusCode.
	 * @since 0.0.1 ~2021.03.20
	 */
	@NotNull
	@Contract(pure = true)
	@Pattern(HTTPRegExp.STATUS_CODE)
	@Override
	String toString();
}
