package com.beyou;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
public class MvcConfig implements WebMvcConfigurer{

    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     //For Categories
    //     exposeDirectory("./category-images", registry);
    //     //For Brands
    //     exposeDirectory("./brand-logos", registry);
    //     //for Products
    //     exposeDirectory("./product-images", registry);
    //     //for site logo in general settings
    //     exposeDirectory("./site-logo", registry);
    // }

    // private void exposeDirectory(String pathPattern,ResourceHandlerRegistry registry){
    //     Path path = Paths.get(pathPattern);
    //     String absolutePath = path.toFile().getAbsolutePath();

    //     String logicalPath = pathPattern.replace("./", "") + "/**";

    //     registry.addResourceHandler(logicalPath)
    //         .addResourceLocations("file:/"+ absolutePath + "/");
    // }
    
    
}
