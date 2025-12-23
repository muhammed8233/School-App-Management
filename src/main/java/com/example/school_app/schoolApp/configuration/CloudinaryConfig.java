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
        config.put("cloud_name", "muhammed8233");
        config.put("api_key", "518328751112733");
        config.put("api_secret","OWlv-QBoee9zgeGckhJEgeHT_hI");
        return new Cloudinary(config);
    }
}
