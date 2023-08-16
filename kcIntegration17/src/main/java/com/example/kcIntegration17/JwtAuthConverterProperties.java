package com.example.kcIntegration17;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/* creata per iniettare le configurazioni di sicurezza del properties in spring boot*/

@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtAuthConverterProperties {
	
	private String resourceId;
    private String principalAttribute;
    
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getPrincipalAttribute() {
		return principalAttribute;
	}
	public void setPrincipalAttribute(String principalAttribute) {
		this.principalAttribute = principalAttribute;
	}
	
	@Override
	public String toString() {
		return "JwtAuthConverterProperties [resourceId=" + resourceId + ", principalAttribute=" + principalAttribute
				+ "]";
	}

    
}
