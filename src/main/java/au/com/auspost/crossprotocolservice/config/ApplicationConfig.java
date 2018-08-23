package au.com.auspost.crossprotocolservice.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter {
	@Value("${elasticsearch.home:/Users/muhammadahmed/java/elasticsearch-6.3.1}")
	private String elasticsearchHome;

	@Value("${elasticsearch.cluster.name:elasticsearch}")
	private String clusterName;

	@Bean
	public Client client() {
		TransportClient client = null;
		try {
			final Settings elasticsearchSettings = Settings.builder().put("client.transport.sniff", true)
					.put("path.home", elasticsearchHome).put("cluster.name", "elasticsearch").build();
			client = new PreBuiltTransportClient(elasticsearchSettings);
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

		// set path extension to false
		configurer.favorPathExtension(false).
		// request parameter ("format" by default) should be used to determine the
		// requested media type
				favorParameter(true).
				// the favour parameter is set to "mediaType" instead of default "format"
				parameterName("mediaType").
				// ignore the accept headers
				ignoreAcceptHeader(true).
				// dont use Java Activation Framework since we are manually specifying the
				// mediatypes required below
				useJaf(false).defaultContentType(MediaType.APPLICATION_XML).mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("json", MediaType.APPLICATION_JSON).mediaType("text", MediaType.TEXT_PLAIN);
	}
}