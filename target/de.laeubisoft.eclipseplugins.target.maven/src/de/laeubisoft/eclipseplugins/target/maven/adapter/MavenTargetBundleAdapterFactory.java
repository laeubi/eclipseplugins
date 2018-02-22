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

import de.laeubisoft.eclipseplugins.target.maven.MavenTargetBundle;
import de.laeubisoft.eclipseplugins.target.maven.provider.MavenTargetBundleLabelProvider;

public class MavenTargetBundleAdapterFactory implements IAdapterFactory {


	private ILabelProvider labelProvider = new MavenTargetBundleLabelProvider();

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof MavenTargetBundle) {
			if (adapterType == ILabelProvider.class) {
				return adapterType.cast(labelProvider);
			}
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { ILabelProvider.class };
	}

}
