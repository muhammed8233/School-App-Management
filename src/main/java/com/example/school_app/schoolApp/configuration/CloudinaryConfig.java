package com.example.school_app.schoolApp.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "CLOUDINARY_CLOUD_NAME");
        config.put("api_key", "CLOUDINARY_API_KEY");
        config.put("api_secret","CLOUDINARY_API_SECRET");
        return new Cloudinary(config);
    }
}
