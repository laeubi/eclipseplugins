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
package de.laeubisoft.eclipseplugins.target.maven.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.pde.ui.target.ITargetLocationEditor;
import org.eclipse.pde.ui.target.ITargetLocationUpdater;

import de.laeubisoft.eclipseplugins.target.maven.MavenTargetLocation;
import de.laeubisoft.eclipseplugins.target.maven.MavenTargetLocationUpdater;
import de.laeubisoft.eclipseplugins.target.maven.editor.MavenTargetLocationEditor;
import de.laeubisoft.eclipseplugins.target.maven.provider.MavenTargetLocationLabelProvider;
import de.laeubisoft.eclipseplugins.target.maven.provider.MavenTargetTreeContentProvider;

/**
 *
 */
public class MavenTargetAdapterFactory implements IAdapterFactory {

	private ILabelProvider labelProvider = new MavenTargetLocationLabelProvider();
	private ITreeContentProvider treeContentProvider = new MavenTargetTreeContentProvider();
	private MavenTargetLocationEditor locationEditor = new MavenTargetLocationEditor();
	private MavenTargetLocationUpdater locationUpdater = new MavenTargetLocationUpdater();

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { ILabelProvider.class, ITreeContentProvider.class, ITargetLocationEditor.class,
				ITargetLocationUpdater.class };
	}

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof MavenTargetLocation) {
			if (adapterType == ILabelProvider.class) {
				return adapterType.cast(labelProvider);
			} else if (adapterType == ITreeContentProvider.class) {
				return adapterType.cast(treeContentProvider);
			} else if (adapterType == ITargetLocationEditor.class) {
				return adapterType.cast(locationEditor);
			} else if (adapterType == ITargetLocationUpdater.class) {
				return adapterType.cast(locationUpdater);
			}
		}
		return null;
	}

}
