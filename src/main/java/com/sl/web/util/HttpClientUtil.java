package com.sl.web.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.sl.web.model.NotHttp200Exception;

public class HttpClientUtil {
	private static final Log log = LogFactory.getLog(HttpClientUtil.class);
	
	private static RequestConfig requestConfig;
    private static Map<String, CloseableHttpClient> httpClientMap = new ConcurrentHashMap<>();
    
    private HttpClientUtil(){}
    
    static{
    	requestConfig = RequestConfig.custom()
    			.setConnectionRequestTimeout(5000).setSocketTimeout(5000).setConnectTimeout(5000)
    			//.setProxy(new HttpHost("149.56.102.220", 3128, "http"))
    			.build();
    }
    
    private static synchronized CloseableHttpClient createClient(String host, int port){
    	String key = host + ":" + port;
    	CloseableHttpClient client = httpClientMap.get(key);
    	
    	if(client == null){
    		Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
            try {
            	SSLContextBuilder builder = new SSLContextBuilder();
				builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
				
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
				socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register(
						"http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
				
			}catch (Exception e) {
				log.warn("create SSLConnectionSocketFactory failed, ssl connection will not be established");
			}
            
            PoolingHttpClientConnectionManager poolConnManager = socketFactoryRegistry != null ?
            		new PoolingHttpClientConnectionManager(socketFactoryRegistry) : new PoolingHttpClientConnectionManager();
            poolConnManager.setMaxTotal(10000);
            poolConnManager.setDefaultMaxPerRoute(5000);
            
            client = HttpClients.custom()
        			.setConnectionManager(poolConnManager)
        			.setDefaultRequestConfig(requestConfig)
        			.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
        			.build();
            
            httpClientMap.put(key, client);
    	}
    	
	    return client;
    }
    
    private static CloseableHttpClient getClient(String url) throws MalformedURLException{
    	URL urlObj = new URL(url);
    	String host = urlObj.getHost();
    	int port = urlObj.getPort();
    	if(port < 0){
    		port = urlObj.getDefaultPort();
    	}
    	if(port < 0){
    		port = 80;
    	}
    	
    	CloseableHttpClient client = httpClientMap.get(host + ":" + port);
    	if(client == null){
    		client = createClient(host, port);
    	}
    	
    	return client;
    }
    
    private static RequestConfig getConfig(String httpProxyIp, Integer httpProxyPort){
    	if(!StringUtils.isEmpty(httpProxyIp) && httpProxyPort != null){
    		return RequestConfig.custom()
        			.setConnectionRequestTimeout(5000).setSocketTimeout(5000).setConnectTimeout(5000)
        			.setProxy(new HttpHost(httpProxyIp, httpProxyPort, "http"))
        			.build();
    	}else{
    		return requestConfig;
    	}
    }
	
	private static HttpPost getHttpPost(String url, String httpProxyIp, Integer httpProxyPort){
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(getConfig(httpProxyIp, httpProxyPort));
		
		return httpPost;
	}
	
	private static HttpGet getHttpGet(String url, String httpProxyIp, Integer httpProxyPort){
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(getConfig(httpProxyIp, httpProxyPort));
		
		return httpGet;
	}
	
	private static HttpHead getHttpHead(String url, String httpProxyIp, Integer httpProxyPort){
		HttpHead httpHead = new HttpHead(url);
		httpHead.setConfig(getConfig(httpProxyIp, httpProxyPort));
		
		return httpHead;
	}
	
	private static void release(HttpRequestBase postGet){
		if(postGet != null){
			try{
				postGet.releaseConnection();
			}catch (Exception ex){
				log.error("release connection error: " + postGet.getURI());
			}
		}
	}
	
	private static void release(CloseableHttpResponse response){
		try {
			if(response != null){
				response.close();
			}
		} catch (IOException e) {
			log.error("close response error");
		}
	}
	
	public static String httpPost(String url, Map<String,String> paramMap, String httpProxyIp, Integer httpProxyPort) throws IOException, NotHttp200Exception{
		CloseableHttpClient httpClient = getClient(url);
		HttpPost httpPost = getHttpPost(url, httpProxyIp, httpProxyPort);
		CloseableHttpResponse response = null;
		
		try {
			if(paramMap != null && !paramMap.isEmpty()){
				List<NameValuePair> nameValuePairArrayList = getNameValuePairList(paramMap);
				UrlEncodedFormEntity param = new UrlEncodedFormEntity(nameValuePairArrayList, StandardCharsets.UTF_8);
				httpPost.setEntity(param);
			}
			
			response = httpClient.execute(httpPost);
			
			int status = response.getStatusLine().getStatusCode();
        	String content = Common.inputStream2String(response.getEntity().getContent(), StandardCharsets.UTF_8, true);
			
			if(status == 200){
				return content;
	        	
	        }else{
	        	throw new NotHttp200Exception(status, content);
	        }
			
		}finally{
			release(response);
        	release(httpPost);
		}
	}
	
	public static String httpPost(String url, Map<String,String> paramMap) throws IOException, NotHttp200Exception{
		return httpPost(url, paramMap, null, null);
	}
	
	public static String httpGet(String url, String httpProxyIp, Integer httpProxyPort) throws IOException, NotHttp200Exception {
		CloseableHttpClient httpClient = getClient(url);
		HttpGet httpGet = getHttpGet(url, httpProxyIp, httpProxyPort);
		CloseableHttpResponse response = null;
		
		try {
			response = httpClient.execute(httpGet);
			
			int status = response.getStatusLine().getStatusCode();
        	String content = Common.inputStream2String(response.getEntity().getContent(), StandardCharsets.UTF_8, true);
        	
        	if(status == 200){
				return content;
	        	
	        }else{
	        	throw new NotHttp200Exception(status, content);
	        }
	        
		}finally{
			release(response);
        	release(httpGet);
		}
	}
	
	public static String httpGet(String url) throws IOException, NotHttp200Exception {
		return httpGet(url, null, null);
	}
	
	public static boolean isResourceExists(String url, String httpProxyIp, Integer httpProxyPort) throws IOException{
		CloseableHttpClient httpClient = getClient(url);
		HttpHead httpHead = getHttpHead(url, httpProxyIp, httpProxyPort);
		CloseableHttpResponse response = null;
		
		try {
			response = httpClient.execute(httpHead);
			
			return response.getStatusLine().getStatusCode() == 200;
	        
		}finally{
			release(response);
        	release(httpHead);
		}
	}
	
	public static boolean isResourceExists(String url) throws IOException{
		return isResourceExists(url, null, null);
	}
	
	private static List<NameValuePair> getNameValuePairList(Map<String, String> paramMap){
		List<NameValuePair> nameValuePairArrayList = new ArrayList<>();
		if(paramMap!=null){
			for(Entry<String, String> entry : paramMap.entrySet()){
				nameValuePairArrayList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		return nameValuePairArrayList;
	}
	
}
