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
package de.laeubisoft.eclipseplugins.target.maven.provider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.laeubisoft.eclipseplugins.target.maven.MavenTargetLocation;
import de.laeubisoft.eclipseplugins.target.maven.adapter.MavenTargetAdapterFactory;

public class MavenTargetLocationLabelProvider implements ILabelProvider {
	private Image image;

	@Override
	public String getText(Object element) {
		if (element instanceof MavenTargetLocation) {
			MavenTargetLocation location = (MavenTargetLocation) element;
			return location.getGroupId() + ":" + location.getArtifactId() + " (" + location.getVersion() + ")";
		}
		return String.valueOf(element);
	}

	@Override
	public Image getImage(Object element) {
		Display current = Display.getCurrent();
		if (image == null && current != null) {

			image = new Image(current, MavenTargetAdapterFactory.class.getResourceAsStream("/icons/m2.gif"));
		}
		return image;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {
		image.dispose();

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

}
