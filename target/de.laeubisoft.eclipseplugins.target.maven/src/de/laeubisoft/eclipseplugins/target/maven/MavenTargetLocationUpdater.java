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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetLocation;
import org.eclipse.pde.ui.target.ITargetLocationUpdater;

public class MavenTargetLocationUpdater implements ITargetLocationUpdater {

	@Override
	public boolean canUpdate(ITargetDefinition target, ITargetLocation targetLocation) {
		return targetLocation instanceof MavenTargetLocation;
	}

	@Override
	public IStatus update(ITargetDefinition target, ITargetLocation targetLocation, IProgressMonitor monitor) {
		MavenTargetLocation location = (MavenTargetLocation)targetLocation;
		location.refresh();
		return Status.OK_STATUS;
	}

}
