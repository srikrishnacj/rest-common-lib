package io.sskrishna.rest.config;

import io.sskrishna.rest.exception.container.RestErrorBuilder;
import io.sskrishna.rest.response.ErrorCodeLookup;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;

public abstract class RestResponseDefaultConfig {

    private ErrorCodeLookup errorCodeLookup;

    public abstract void configLookupFiles() throws IOException;

    @Bean
    public ErrorCodeLookup errorCodeLookup() throws IOException {
        this.errorCodeLookup = new ErrorCodeLookup();
        this.configLookupFiles();
        return this.errorCodeLookup;
    }

    @Bean
    public RestErrorBuilder restErrorBuilder(ErrorCodeLookup errorCodeLookup) {
        return new RestErrorBuilder(errorCodeLookup);
    }
    
    public void addDir(String path) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("/error_lookup_codes/**");
        for (Resource resource : resources) {
            this.addFile(resource);
        }
    }

    public void addFile(String file) throws IOException {
        this.assertFileExists(file);
        Resource resource = new ClassPathResource(file);
        errorCodeLookup.addFromLookupFile(resource);
    }

    private void addFile(Resource resource) throws IOException {
        this.assertFileExists(resource);
        errorCodeLookup.addFromLookupFile(resource);
    }

    private void assertFileExists(String path) throws IOException {
        if (StringUtils.isEmpty(path)) {
            throw new RuntimeException("path for error code lookup file received as null or empty");
        }
        Resource resource = new ClassPathResource(path);
        this.assertFileExists(resource);
    }

    private void assertFileExists(Resource resource) throws IOException {
        if (resource.exists() == false) {
            throw new RuntimeException("path for error code lookup file does not exits: " + resource.getFile());
        }
    }
}
