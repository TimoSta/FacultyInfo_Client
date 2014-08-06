package de.uni_passau.facultyinfo.client.model.connection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class RestConnection<T> {
	private static final String REST_LOCATION = "http://176.28.10.221:8080/FacultyInfo";
	// private static final String REST_LOCATION =
	// "http://10.0.2.2:8080/FacultyInfo";

	private Class<T> targetClass;

	public RestConnection(Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	public List<T> getRessourceAsList(String ressource) {

		try {
			URL url = new URL(REST_LOCATION + ressource);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			try {
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						urlConnection.getInputStream());

				ObjectMapper mapper = new ObjectMapper();
				mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
				mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

				List<T> out = mapper.readValue(
						bufferedInputStream,
						mapper.getTypeFactory().constructCollectionType(
								List.class, targetClass));

				return out;

			} catch (JsonMappingException e) {
				e.printStackTrace();
				return null;

			} catch (JsonParseException e) {
				e.printStackTrace();
				return null;

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				urlConnection.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public T getRessource(String ressource) {

		try {
			URL url = new URL(REST_LOCATION + ressource);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			try {
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						urlConnection.getInputStream());

				ObjectMapper mapper = new ObjectMapper();
				mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
				mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

				T out = mapper.readValue(bufferedInputStream, mapper
						.getTypeFactory().constructType(targetClass));

				return out;

			} catch (JsonMappingException e) {
				e.printStackTrace();
				return null;

			} catch (JsonParseException e) {
				e.printStackTrace();
				return null;

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				urlConnection.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
