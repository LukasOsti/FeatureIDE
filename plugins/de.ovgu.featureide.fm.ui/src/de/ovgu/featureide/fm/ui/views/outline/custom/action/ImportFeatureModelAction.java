/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2017  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 *
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.fm.ui.views.outline.custom.action;

import static de.ovgu.featureide.fm.core.localization.StringTable.IMPORT_FEATURE_MODEL;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;

import de.ovgu.featureide.fm.core.io.manager.IFeatureModelManager;
import de.ovgu.featureide.fm.ui.FMUIPlugin;
import de.ovgu.featureide.fm.ui.editors.ImportFeatureModelDialog;
import de.ovgu.featureide.fm.ui.editors.featuremodel.operations.FeatureModelOperationWrapper;
import de.ovgu.featureide.fm.ui.editors.featuremodel.operations.ImportFeatureModelOperation;

/**
 * Action to import a feature model.
 *
 * @author Kevin Jedelhauser
 * @author Johannes Herschel
 */
public class ImportFeatureModelAction extends Action {

	private static final ImageDescriptor ICON = FMUIPlugin.getDefault().getImageDescriptor("icons/import_wiz.gif");

	/**
	 * The feature model manager of the importing model.
	 */
	private final IFeatureModelManager featureModelManager;

	public ImportFeatureModelAction(IFeatureModelManager featureModelManager) {
		super(IMPORT_FEATURE_MODEL, ICON);
		this.featureModelManager = featureModelManager;

		setToolTipText(IMPORT_FEATURE_MODEL);
	}

	@Override
	public void run() {
		final ImportFeatureModelDialog dialog = new ImportFeatureModelDialog(null, featureModelManager);
		final int result = dialog.open();
		if (result == Window.OK) {
			FeatureModelOperationWrapper.run(new ImportFeatureModelOperation(featureModelManager, dialog.getRelativePath(), dialog.getAlias()));
		}
	}
}
