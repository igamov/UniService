package io.vscale.uniservice.security.rest.handlers;

import io.vscale.uniservice.security.rest.errors.APIError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

/**
 * 10.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component("restAuthenticationEntryPoint")
public class RESTAuthenticationEntryPoint extends BasicAuthenticationEntryPoint{

    @Value("${realm.type}")
    private String realm;

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException ex)
                                                                            throws IOException{

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        resp.setCharacterEncoding("UTF-8");

        APIError apiError = new APIError(HttpStatus.UNAUTHORIZED);
        apiError.setMessage("Пользователь не вошёл в систему");
        apiError.setDebugMessage(ex.getMessage());

        PrintWriter pw = resp.getWriter();

        StringBuilder sb = new StringBuilder();
        sb.append("{ \"apierror\": {")
          .append("\"status\": ").append(apiError.getStatus()).append(", ")
          .append("\"timestamp\": ").append(apiError.getErrorTime().format(formatter)).append(", ")
          .append("\"message\": ").append(apiError.getMessage()).append(", ")
          .append("\"debugMessage\": ").append(apiError.getDebugMessage())
          .append("}").append("}");

        pw.println(sb.toString());

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(this.realm);
        super.afterPropertiesSet();
    }
}
