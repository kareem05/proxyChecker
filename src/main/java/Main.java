import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
public class Main {

    public static final Logger logger = Logger.getLogger(Main.class.getName());
    static String proxyUsername = " incorta";
    static String proxyPassword = "Incorta_1234";
    static String proxyHost = "3.17.179.100";
    static int proxyPort = 8888;
    public static void main(String[] args) throws IOException {
	// write your code here

        executeRESTRequest();

    }

     static void executeRESTRequest() throws IOException {
        String uRI = "https://www.google.com";

        HttpGet httpGet = initHTTPGet(uRI);

         // Make the request.
            try (CloseableHttpClient httpClient = getHTTPClient();
                 CloseableHttpResponse response = httpClient.execute(httpGet);) {

                logger.info("REST request to " + uRI + "Sent.");

                int statusCode = response.getStatusLine().getStatusCode();

                String Body = retrieveBody(response.getEntity().getContent());

                System.out.println("statusCode----> " + statusCode );
                System.out.println("Body----> " + Body );

            }



    }

    static String retrieveBody(InputStream inputStream) throws IOException {

        String result = "";

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream)
            );

            String inputLine;

            while ( (inputLine = in.readLine() ) != null ) {
                result += inputLine;
                result += "\n";
            }

            in.close();

        return result;
    }

    private static CloseableHttpClient getHTTPClient() {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();


//        if (proxyUsername != null && !"".equals(proxyUsername)) {
//
//            CredentialsProvider credsProvider = new BasicCredentialsProvider();
//            credsProvider.setCredentials(
//                    new AuthScope(proxyHost, proxyPort),
//                    new UsernamePasswordCredentials(proxyUsername, proxyPassword));
//
//            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
//        }

        return httpClientBuilder.build();
    }

    private static HttpGet initHTTPGet(String uRI) {

        Builder requestConfigBuilder = RequestConfig.custom();
//                .setConnectTimeout(CONNECTION_TIMEOUT)
//                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
//                .setSocketTimeout(READ_TIMEOUT)
//                .setContentCompressionEnabled(ENABLE_COMPRESSION);

        setProxyConfigs(requestConfigBuilder);

        RequestConfig requestConfig = requestConfigBuilder.build();

        //Set up the HTTP objects needed to make the request.

        HttpGet httpGet = new HttpGet(uRI);
        httpGet.setConfig(requestConfig);

//        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
//        httpGet.addHeader(HttpHeaders.ACCEPT_ENCODING, ENCODING);

        return httpGet;
    }

    private static void setProxyConfigs(Builder requestConfigBuilder) {
        if (proxyHost != null && !"".equals(proxyHost)) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            requestConfigBuilder.setProxy(proxy);
        }
    }
}
