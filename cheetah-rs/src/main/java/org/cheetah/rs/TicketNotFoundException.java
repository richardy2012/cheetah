package org.cheetah.rs;


/**
 * @author Max
 */
@ApiExceptionMetadata(code = ApiConstants.TICKET_NOT_FOUND_ERROR, message = "ticket not found.")
public class TicketNotFoundException extends ApiException {
    private static final long serialVersionUID = 763039895911862886L;
}
