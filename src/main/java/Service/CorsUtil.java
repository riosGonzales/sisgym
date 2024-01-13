package Service;

import javax.ws.rs.core.Response;

public class CorsUtil {

    public static Response buildCorsResponse() {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }
    
    public static Response buildCORSResponse(String jsonResponse) {
        return Response.ok(jsonResponse)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }
    
    public static Response buildCorsResponseToken() {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    public static Response buildCorsResponseError() {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    public static Response buildCorsResponseToken(Object entity) {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                .header("Access-Control-Allow-Credentials", "true")
                .entity(entity)
                .build();
    }
    
    public static Response buildCorsResponse(Object entity) {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .entity(entity)
                .build();
    }

    public static Response buildOkResponse(String responseBody) {
        return buildResponse(Response.Status.OK, responseBody);
    }

    public static Response buildResponse(Response.Status status, String responseBody) {
        return Response.status(status)
                .entity(responseBody)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    public static Response buildNotFoundResponse(String errorMessage) {
        return buildResponse(Response.Status.NOT_FOUND, "{\"error\": \"" + errorMessage + "\"}");
    }

    public static Response buildUnauthorizedResponse() {
        return buildResponse(Response.Status.UNAUTHORIZED, "{\"error\": \"Error: Token no válido\"}");
    }
}
