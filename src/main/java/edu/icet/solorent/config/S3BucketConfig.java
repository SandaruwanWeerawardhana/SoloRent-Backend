//package edu.icet.solorent.config;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class S3BucketConfig {
//
//    @Value("${aws.s3.access-key}")
//    private String accessKey;
//
//    @Value("${aws.s3.secret-key}")
//    private String secretKey;
//
//    @Value("${aws.s3.region}")
//    private String region;
//
//    private String endpointUrl;
//
//    @Bean
//    public AmazonS3 amazonS3() {
//        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//
//        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(region);
//
//        if (endpointUrl != null && !endpointUrl.isEmpty()) {
//            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, region))
//                    .withPathStyleAccessEnabled(true);
//        }
//
//        return builder.build();
//    }
//
//}
