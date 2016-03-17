package ch.mydrive;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AppProps {
	
	public static AppProps INSTANCE = new AppProps();
	
	static Path userHomePath;
	static Path appPropsFilePath;
	
	private Properties props = new Properties();
	
	private AppProps (){
		try {
			init();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void init() throws IOException {
		userHomePath = Paths.get(System.getProperty("user.home"), ".mydrive");
		appPropsFilePath = userHomePath.resolve("app.properties");
		if (!Files.exists(userHomePath)){
			Files.createDirectory(userHomePath);
		}		
		if (!Files.exists(appPropsFilePath)){
			Files.createFile(appPropsFilePath);
		}
		props.load(new FileInputStream(appPropsFilePath.toFile()));
	}
	
	private void store(){
		try (FileOutputStream fos = new FileOutputStream(appPropsFilePath.toFile())){
			props.store(fos, null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public int getInt(String key, int defaultValue){
		String value = props.getProperty(key);
		if (value == null || value.isEmpty()){
			return defaultValue;
		}
		return Integer.parseInt(value);
	}
	
	public synchronized void setInt(String key, int value){
		props.setProperty(key, String.valueOf(value));
		store();
	}
	
	public String getString(String key, String defaultValue){
		String value = props.getProperty(key);
		if (value == null || value.isEmpty()){
			return defaultValue;
		}
		return value;
	}
	
	public synchronized void setString(String key, String value){
		props.setProperty(key, value);
		store();
	}
	
	public boolean getBool(String key, boolean defaultValue){
		String value = props.getProperty(key);
		if (value == null || value.isEmpty()){
			return defaultValue;
		}
		return Boolean.parseBoolean(value);
	}
	
	public synchronized void setBool(String key, boolean value){
		props.setProperty(key, String.valueOf(value));
		store();
	}
}
