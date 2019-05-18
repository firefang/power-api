package io.github.firefang.power.engine.request;

/**
 * Interface used for send a request. A IRequestClient object will be shared
 * within a project.
 * 
 * @author xinufo
 *
 */
public interface IRequestClient {

    /**
     * Initialize the client. This method is invoked when ResolveApiType.
     */
    void init();

    /**
     * Connect to the server
     * 
     * @param request
     */
    void connect(IRequestInfo request);

    /**
     * Close the connection
     */
    void close();

    /**
     * Send a request
     * 
     * @param request
     * @return
     */
    IResponseInfo request(IRequestInfo request);

    /**
     * Destroy the client. This methos is invoked when ProjectEnd
     */
    void destroy();

    /**
     * Create a IRequestBuilder object
     * 
     * @return
     */
    IRequestBuilder newBuilder();

    /**
     * Get the IContentResolver object by request type
     * 
     * @param type request type
     * @return if the type is supported then return the IContentResolver object,
     *         otherwise return null
     */
    IContentResolver resolver(String type);

}
