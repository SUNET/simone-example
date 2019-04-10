package se.uhr.simone.restbucks.control.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ApplicationManifest {

	private final String implementationVersion;

	private final String implementationSCMVersion;

	private final String buildNumber;

	private final String buildTime;

	public ApplicationManifest(InputStream is) throws IOException {
		Manifest manifest = new Manifest(is);
		Attributes attributes = manifest.getMainAttributes();

		this.implementationVersion = attributes.getValue("Implementation-Version");
		this.implementationSCMVersion = attributes.getValue("Implementation-SCM-Revision");
		this.buildNumber = attributes.getValue("Build-Number");
		this.buildTime = attributes.getValue("Build-Time");
	}

	public String getImplementationVersion() {
		return implementationVersion;
	}

	public String getImplementationSCMVersion() {
		return implementationSCMVersion;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public String getBuildTime() {
		return buildTime;
	}

}
