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
package de.ovgu.featureide.fm.attributes.view.operations;

import static de.ovgu.featureide.fm.core.localization.StringTable.CHANGE_ATTRIBUTE_CONFIGURABLE_OPERATION_NAME;

import de.ovgu.featureide.fm.attributes.AttributeUtils;
import de.ovgu.featureide.fm.attributes.base.IFeatureAttribute;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.event.FeatureIDEEvent;
import de.ovgu.featureide.fm.core.base.event.FeatureIDEEvent.EventType;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.ovgu.featureide.fm.core.io.manager.IFeatureModelManager;
import de.ovgu.featureide.fm.ui.editors.featuremodel.operations.AbstractFeatureModelOperation;

/**
 * Operation to change whether a feature attribute is configurable. Enables undo/redo functionality.
 * 
 * @author Johannes Herschel
 */
public class ChangeAttributeConfigurableOperation extends AbstractFeatureModelOperation {

	/**
	 * The name of the feature containing the attribute to be modified.
	 */
	private final String featureName;
	/**
	 * The name of the attribute to be modified.
	 */
	private final String attributeName;
	/**
	 * Whether the attribute is configurable after the operation.
	 */
	private final boolean newConfigurable;

	/**
	 * Whether the attribute is configurable before the operation.
	 */
	private final boolean oldConfigurable;

	public ChangeAttributeConfigurableOperation(IFeatureModelManager featureModelManager, IFeatureAttribute attribute, boolean newConfigurable) {
		super(featureModelManager, CHANGE_ATTRIBUTE_CONFIGURABLE_OPERATION_NAME);
		featureName = attribute.getFeature().getName();
		attributeName = attribute.getName();
		this.newConfigurable = newConfigurable;

		oldConfigurable = attribute.isConfigurable();
	}

	@Override
	protected FeatureIDEEvent operation(IFeatureModel featureModel) {
		final IFeatureAttribute attribute = AttributeUtils.getAttribute(featureModel, featureName, attributeName);
		if (attribute != null) {
			attribute.setConfigurable(newConfigurable);
			return new FeatureIDEEvent(attribute, EventType.FEATURE_ATTRIBUTE_CHANGED, true, attribute.getFeature());
		}
		return FeatureIDEEvent.getDefault(EventType.FEATURE_ATTRIBUTE_CHANGED);
	}

	@Override
	protected FeatureIDEEvent inverseOperation(IFeatureModel featureModel) {
		final IFeatureAttribute attribute = AttributeUtils.getAttribute(featureModel, featureName, attributeName);
		if (attribute != null) {
			attribute.setConfigurable(oldConfigurable);
			return new FeatureIDEEvent(attribute, EventType.FEATURE_ATTRIBUTE_CHANGED, true, attribute.getFeature());
		}
		return FeatureIDEEvent.getDefault(EventType.FEATURE_ATTRIBUTE_CHANGED);
	}

	@Override
	protected int getChangeIndicator() {
		return FeatureModelManager.CHANGE_ATTRIBUTES;
	}
}
