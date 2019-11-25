import annotations.Roles;
import anotherData.MethodData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER+1000) // launch after original plugins
public class CustomPlugins implements OperationBuilderPlugin {
    private final static ObjectMapper objectMapper =new ObjectMapper();

    public void apply(OperationContext operationContext) {
        MethodData methodData = new MethodData();
        Roles roles = operationContext.findAnnotation(Roles.class).orNull();
        if (roles != null) {
            methodData.setRoles(roles.value());
        }
        try {
            operationContext.operationBuilder().notes(objectMapper.writeValueAsString(methodData));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
