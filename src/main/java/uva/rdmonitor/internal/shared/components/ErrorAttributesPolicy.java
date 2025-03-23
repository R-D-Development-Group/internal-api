package uva.rdmonitor.internal.shared.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class ErrorAttributesPolicy extends DefaultErrorAttributes {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        errorAttributes.put("error", "An unexpected error occurred");

        if (!"local".equals(activeProfile)) {
            errorAttributes.remove("message");
        }

        errorAttributes.remove("path");
        errorAttributes.remove("trace");

        return errorAttributes;
    }
}
