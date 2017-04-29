package com.omnidrive.gdrive.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class Crud {

	private static final String APPLICATION_NAME = "OmniDrive";
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/drive-java-quickstart");
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static HttpTransport HTTP_TRANSPORT;
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	// A Static DriveService that is being reused throughout Application.
	static Drive driveservice = null;

	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = Crud.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	public static Drive getDriveService() throws IOException {
		Credential credential = authorize();
		if (driveservice == null) {
			// Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY,
			// credential).setApplicationName(APPLICATION_NAME).build();

			driveservice = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();
		}
		return driveservice;
	}

	public static List<File> listGdriveObjests(int limit) throws IOException {
		// limit integer will limit number of files, instead of fetching all of
		// them.
		getDriveService();

		FileList result = driveservice.files().list().setMaxResults(limit).execute();
		List<File> files = result.getItems();
		return files;
	}

	public static String createGdriveFolder(String folderName) throws IOException {
		// this method creates new folder in GDrive and return its id
		File fileMetadata = new File();
		fileMetadata.setTitle(folderName);
		fileMetadata.setMimeType("application/vnd.google-apps.folder");
		File file = driveservice.files().insert(fileMetadata).setFields("id").execute();
		// System.out.println("Folder ID: " + file.getId());
		return file.getId();
	}
	


}
