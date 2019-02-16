package com.sl.web.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class Common {
	private Common(){
	}
	
	public static void sleepWithInterrupt(long interval){
		try {
			Thread.sleep(interval);
			
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * uuid32
	 */
	public static String uuid32() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}
	
	public static int getCpuNums(){
		return Runtime.getRuntime().availableProcessors();
	}
	
	public static double toFixed(double value, int digits){
		BigDecimal big = BigDecimal.valueOf(value).setScale(digits, BigDecimal.ROUND_HALF_UP);
		return big.doubleValue();
	}
	
	public static void closeStream(Closeable os){
		try{
			if(os != null){
				os.close(); 
			}
 		}catch(Exception ex){
 			//ignore exception
 		}
	}
	
	public static <T> Set<T> retain(List<Set<T>> input){ 
		if(CollectionUtils.isEmpty(input)){ 
			return new HashSet<>(); 
		}
		
		Set<T> result = null;
		for(Set<T> item : input){
			if(item == null){
				continue;
			}
			
			if(result == null){
				result = new HashSet<>(item);
				
			}else{
				result.retainAll(item);
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Set<T> retain(Set<T>... input){ 
		if(input == null || input.length == 0){ 
			return new HashSet<>(); 
		}
		
		return retain(Arrays.asList(input));
	}
	
	public static byte[] objectToBytes(Object obj) throws IOException{
     	ByteArrayOutputStream bos = new ByteArrayOutputStream();
     	ObjectOutputStream oos = new ObjectOutputStream(bos);  
     	try{
     		oos.writeObject(obj);
    		oos.flush();
     	}finally{
     		closeStream(bos);
     		closeStream(oos);
     	}
     	
     	return bos.toByteArray();
	}
	
	public static Object bytesToObject(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream bis = new ByteArrayInputStream(data); 
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = null;
        try{
        	obj = ois.readObject();
        }finally{
        	closeStream(bis);
        	closeStream(ois);
        }
        return obj;
	}
	
	
	public static String inputStream2String(InputStream is, Charset charSet, boolean closeOnReturn) throws IOException{ 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        try{
        	byte[] tempbytes = new byte[128];
            int byteread = 0;
            while ((byteread = is.read(tempbytes)) != -1) {
            	baos.write(tempbytes, 0, byteread);
            }
        }finally{
        	closeStream(baos);
        	if(closeOnReturn){
        		closeStream(is);
        	}
        }
        return new String(baos.toByteArray(), charSet);
	}
	
	public static byte[] inputStream2Bytes(InputStream is, boolean closeOnReturn) throws IOException{ 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
        	byte[] tempbytes = new byte[128];
            int byteread = 0;
            while ((byteread = is.read(tempbytes)) != -1) {
            	baos.write(tempbytes, 0, byteread);
            }
        }finally{
        	closeStream(baos);
        	if(closeOnReturn){
        		closeStream(is);
        	}
        }
        
        return baos.toByteArray();
	}
	
	public static void writeBytesFromFile(String file, OutputStream os) throws IOException{
        try(InputStream in = new FileInputStream(file)){
        	byte[] tempbytes = new byte[128];
            int byteread = 0;
            
        	while ((byteread = in.read(tempbytes)) != -1) {
        		os.write(tempbytes, 0, byteread);
    		}
    		
    		os.flush();
        }
	}

	public static boolean pattern(String reg,String inputStr){
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(inputStr);
		return m.matches();
	}
	
	private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	public static String bytes2HexStr(byte[] data){
		char[] out = new char[data.length << 1];
		
		int j = 0;
        for (int i = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
	}
	
	public static byte[] hexStr2Bytes(String hexStr){
		hexStr = hexStr.toLowerCase();
		int len = hexStr.length();
		byte[] retBytes = new byte[len / 2];
		
		for(int i=0, j=0; i<len; i+=2, j++){
			retBytes[j] = (byte) (Arrays.binarySearch(DIGITS_LOWER, hexStr.charAt(i)) << 4 | Arrays.binarySearch(DIGITS_LOWER, hexStr.charAt(i+1)));
		}
		
		return retBytes;
	}
	
	public static List<byte[]> splitBytesToQualifiedList(int splitSize, byte[] data){
		List<byte[]> retList = new ArrayList<>();
		
		int startIndex = 0;
		int endIndex = 0;
		
		while(startIndex < data.length){
			endIndex = startIndex + splitSize;
			endIndex = endIndex<=data.length? endIndex: data.length;
			retList.add(Arrays.copyOfRange(data, startIndex, endIndex));
			startIndex = endIndex;
		}
		
		return retList;
	}
	
	/**
	 * md5 with utf8
	 * @return return null if error occurred
	 */
	public static String md5(String input) {
        try{
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	
            md.update(input.getBytes(StandardCharsets.UTF_8));
            
            return bytes2HexStr(md.digest());
            
        }catch(Exception e){
            return null;
        }
    }
	
	/**
	 * get string value from map
	 * @param map
	 * @param key
	 * @param canEmpty  canEmpty==false means the method will return null even empty string was found in map  
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getMapString(Map map, String key, boolean canEmpty){
		Object obj = map.get(key);
		if(obj != null){
			String tmpStr = obj.toString();
			if(!canEmpty){
				return tmpStr.length() > 0? tmpStr : null;
			}else{
				return tmpStr;
			}
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static int getMapInt(Map map, String key, int defaultValue){
		Object obj = map.get(key);
		return obj == null? defaultValue : Integer.parseInt(obj.toString());
	}
	
	@SuppressWarnings("rawtypes")
	public static double getMapDouble(Map map, String key, double defaultValue){
		Object obj = map.get(key);
		return obj == null? defaultValue : Double.parseDouble(obj.toString());
	}
	
	@SuppressWarnings("rawtypes")
	public static Long getMapLong(Map map, String key){
		Object obj = map.get(key);
		return obj == null? null : Long.parseLong(obj.toString());
	}
	
	/**
	 * subString.
	 * @param value
	 * @param wantLength  
	 * @return if wantLength >= value.length, return the value itself 
	 */
	public static String subString(String value, int wantLength){
		if(value == null){
			return value;
		}
		
		if(wantLength < value.length()){
			return value.substring(0, wantLength);
		}else{
			return value;
		}
	}
	
	/**
	 * get the real request ip
	 */
	public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
        	ip = request.getHeader("Proxy-Client-IP");
        	
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP"); 
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();
                if(!StringUtils.isEmpty(ip) && (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1"))){
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ip = inet.getHostAddress();
                    } catch (Exception e) {
                    	//ignore
                    }
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) { 
                String strIp = ips[index];
                if (!(unknown.equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
	}
}
