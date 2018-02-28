/*******************************************************************************
 * Copyright (c) 2018 Christoph Läubrich
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *      Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package de.laeubisoft.eclipseplugins.target.maven;

import java.io.File;
import java.util.jar.Manifest;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.frameworkadmin.BundleInfo;
import org.eclipse.pde.core.target.TargetBundle;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

public class MavenTargetBundle extends TargetBundle {

	private TargetBundle bundle;
	private Status status;
	private BundleInfo bundleInfo;
	private boolean isWrapped;

	public BundleInfo getBundleInfo() {
		if (bundle == null) {
			return bundleInfo;
		}
		return bundle.getBundleInfo();
	}

	public boolean isSourceBundle() {
		if (bundle == null) {
			return false;
		}
		return bundle.isSourceBundle();
	}

	public BundleInfo getSourceTarget() {
		if (bundle == null) {
			return null;
		}
		return bundle.getSourceTarget();
	}

	public boolean isFragment() {
		if (bundle == null) {
			return false;
		}
		return bundle.isFragment();
	}

	public String getSourcePath() {
		if (bundle == null) {
			return null;
		}
		return bundle.getSourcePath();
	}

	public MavenTargetBundle(BundleInfo bundleInfo, File file) {
		this.bundleInfo = bundleInfo;
		try {
			bundle = new TargetBundle(file);
		} catch (Exception ex) {
			status = new Status(Status.ERROR, MavenTargetBundle.class.getPackage().getName(),
					bundleInfo.getSymbolicName() + " (" + createBundleVersion(bundleInfo) + ") is not a bundle", ex);
			try {
				File wrappedFile = new File(file.getParentFile(), file.getName() + ".wrappedjar");
				if (!wrappedFile.exists() || wrappedFile.lastModified() < file.lastModified()) {
					try (Jar jar = new Jar(file)) {
						Manifest originalManifest = jar.getManifest();
						try (Analyzer analyzer = new Analyzer();) {
							analyzer.setJar(jar);
							if (originalManifest != null) {
								analyzer.mergeManifest(originalManifest);
							}
							analyzer.setProperty(Analyzer.IMPORT_PACKAGE, "*;resolution:=optional");
							analyzer.setProperty(Analyzer.EXPORT_PACKAGE, "*");
							analyzer.setProperty(Analyzer.BUNDLE_SYMBOLICNAME, createSymbolicName(bundleInfo));
							analyzer.setBundleVersion(createBundleVersion(bundleInfo));
							jar.setManifest(analyzer.calcManifest());
							jar.write(wrappedFile);
						}
					}
				}
				bundle = new TargetBundle(wrappedFile);
				isWrapped = true;
			} catch (Exception e) {
				// not possible then
				String message = bundleInfo.getSymbolicName() + " (" + createBundleVersion(bundleInfo)
						+ ") is not a bundle and cannot be wrapped as such ";
				if (e.getMessage() != null) {
					message += " (" + e.getMessage() + ")";
				}
				status = new Status(Status.ERROR, MavenTargetBundle.class.getPackage().getName(), message, e);
			}

		}
	}

	private String createBundleVersion(BundleInfo bundleInfo) {
		String version = bundleInfo.getVersion();
		if (version == null || version.isEmpty()) {
			return "0";
		}
		return version.replaceAll("[^a-zA-Z0-9-]", "_").replaceAll("__+", "_");
	}

	private String createSymbolicName(BundleInfo bundleInfo) {
		return bundleInfo.getSymbolicName()
				.replaceAll("[^a-zA-Z0-9-]", "_").replaceAll("__+", "_");
	}

	public boolean isWrapped() {
		return isWrapped;
	}

	@Override
	public IStatus getStatus() {
		if (bundle == null) {
			if (status == null) {
				return Status.OK_STATUS;
			}
			return status;
		}
		return bundle.getStatus();
	}

	@Override
	public int hashCode() {
		return getBundleInfo().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MavenTargetBundle) {
			MavenTargetBundle other = (MavenTargetBundle) obj;
			return getBundleInfo().equals(other.getBundleInfo());
		}
		return false;
	}
}
