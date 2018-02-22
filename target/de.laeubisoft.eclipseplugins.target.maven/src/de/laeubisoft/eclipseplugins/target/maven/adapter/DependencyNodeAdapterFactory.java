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

import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import de.laeubisoft.eclipseplugins.target.maven.provider.DependencyNodeLabelProvider;
import de.laeubisoft.eclipseplugins.target.maven.provider.MavenTargetTreeContentProvider;

public class DependencyNodeAdapterFactory implements IAdapterFactory {
	
	private ITreeContentProvider treeContentProvider = new MavenTargetTreeContentProvider();
	private ILabelProvider labelProvider = new DependencyNodeLabelProvider();

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof DependencyNode) {
			if (adapterType == ITreeContentProvider.class) {
				return adapterType.cast(treeContentProvider);
			} else if (adapterType == ILabelProvider.class) {
				return adapterType.cast(labelProvider);
			}
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[]{ITreeContentProvider.class, ILabelProvider.class};
	}

}
