package com.omnidrive.gdrive.service;

/*
@OmniDrive                                                
@author="heminpanchal11"

@modules
[0]GDrive Authenticator(Offline)
[0]GDrive CRUD Class
	[0]Fetch objects
	[0]download objects
	[0]Upload objects
	[0]Delete objects
	
*/

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import com.omnidrive.gdrive.service.*;

public class Application {

	public static void main(String[] args) throws IOException {

		Crud crudservice= new Crud();
		System.out.println(crudservice.fetchFiles(10));
		
	}

}
