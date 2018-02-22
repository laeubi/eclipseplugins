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
package de.laeubisoft.eclipseplugins.target.maven.editor;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetLocation;
import org.eclipse.pde.ui.target.ITargetLocationWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.laeubisoft.eclipseplugins.target.maven.MavenTargetLocation;

public class MavenTargetLocationWizard extends Wizard implements ITargetLocationWizard {

	private Text artifactId;
	private Text groupId;
	private Text version;
	private CCombo type;
	private Button includeDependencies;
	private MavenTargetLocation targetLocation;
	private CCombo scope;

	public MavenTargetLocationWizard() {
		this(null);
	}

	public MavenTargetLocationWizard(MavenTargetLocation targetLocation) {
		this.targetLocation = targetLocation;
		setWindowTitle("Maven Artifact Target Entry");
		addPage(new WizardPage(targetLocation == null ? "Add a new Maven dependency" : "Edit Maven Dependency") {

			

			@Override
			public void createControl(Composite parent) {
				Composite composite = new Composite(parent, SWT.NONE);
				setControl(composite);
				composite.setLayout(new GridLayout(2, false));
				new Label(composite, SWT.NONE).setText("Group Id");
				groupId = fill(new Text(composite, SWT.BORDER));
				new Label(composite, SWT.NONE).setText("Artifact Id");
				artifactId = fill(new Text(composite, SWT.BORDER));
				new Label(composite, SWT.NONE).setText("Version");
				version = fill(new Text(composite, SWT.BORDER));
				new Label(composite, SWT.NONE).setText("Type");
				type = new CCombo(composite, SWT.BORDER);
				type.add("jar");
				type.add("bundle");
				new Label(composite, SWT.NONE).setText("Include dependecies");
				includeDependencies = new Button(composite, SWT.CHECK);
				new Label(composite, SWT.NONE).setText("Dependecy scope");
				scope = new CCombo(composite, SWT.BORDER);
				scope.add("compile");
				scope.add("test");
				scope.setEnabled(false);
				includeDependencies.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent e) {
						scope.setEnabled(includeDependencies.getSelection());
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});
				if (targetLocation != null) {
					artifactId.setText(targetLocation.getArtifactId());
					groupId.setText(targetLocation.getGroupId());
					version.setText(targetLocation.getVersion());
					includeDependencies.setSelection(targetLocation.isIncludeDependencies());
					type.setText(targetLocation.getArtifactType());
					scope.setText(targetLocation.getDependencyScope());
				} else {
					artifactId.setText("");
					groupId.setText("");
					version.setText("");
					includeDependencies.setSelection(false);
					type.setText("jar");
					scope.setText("compile");
				}
			}

			private Text fill(Text text) {
				GridData data = new GridData(GridData.FILL_HORIZONTAL);
				data.grabExcessHorizontalSpace = true;
				text.setLayoutData(data);
				return text;
			}
		});

	}

	@Override
	public void setTarget(ITargetDefinition target) {
	}

	@Override
	public ITargetLocation[] getLocations() {
		return new ITargetLocation[] { targetLocation };
	}

	@Override
	public boolean performFinish() {
		if (targetLocation == null) {
			targetLocation = new MavenTargetLocation(groupId.getText(), artifactId.getText(), version.getText(),
					type.getText(), includeDependencies.getSelection(), scope.getText());
		} else {
			targetLocation.update(artifactId.getText(), groupId.getText(), version.getText(), type.getText(),
					includeDependencies.getSelection(), scope.getText());
		}
		return true;
	}

}
