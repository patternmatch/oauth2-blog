package com.patternmatch.oauth2blog.config;

import static com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder.standard;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.google.gson.Gson;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Base64;
import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class RdsDataSourceConfig {

    private final Gson gson = new Gson();

    @Bean
    public DataSource dataSource() {
        final AwsSecret dbCredentials = getSecret();

        return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://oauth2-blog-db.cauep1t46hcq.eu-west-1.rds.amazonaws.com:5432/springboot_blog")
                .username(dbCredentials.getUsername())
                .password(dbCredentials.getPassword())
                .build();
    }

    /**
     * Fetches data base credentials from AWS Secretes Manager.
     * This is true copy-paste from the AWS console.
     *
     * @return data base credentials
     */
    private AwsSecret getSecret() {

        String secretName = "dev/oauth2_blog";

        // Create a Secrets Manager client
        AWSSecretsManager client = standard()
                .withRegion(Regions.EU_WEST_1)
                .build();

        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        // We rethrow the exception by default.

        String secret, decodedBinarySecret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException e) {
            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InternalServiceErrorException e) {
            // An error occurred on the server side.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidParameterException e) {
            // You provided an invalid value for a parameter.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidRequestException e) {
            // You provided a parameter value that is not valid for the current state of the resource.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (ResourceNotFoundException e) {
            // We can't find the resource that you asked for.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        }

        // Decrypts secret using the associated KMS CMK.
        // Depending on whether the secret is a string or binary, one of these fields will be populated.
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            return gson.fromJson(secret, AwsSecret.class);
        } else {
            // leave this branch
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
        }

        return null;
    }

    /**
     * Just a simple credentials holder.
     */
    public static class AwsSecret {

        private final String username;
        private final String password;

        public AwsSecret(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
