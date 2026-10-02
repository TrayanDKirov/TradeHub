package bg.sofia.uni.fmi.tdkirov.tradehub.dto;

/**
 * Wraps the data of a response together with a message about the request.
 *
 * @param message the message for the user and the developer.
 * @param data    the returned data, may be null when there is nothing to return.
 * @param <T>     the type of the returned data.
 */
public record ApiResponse<T>(MessageResponseDto message, T data) {
}