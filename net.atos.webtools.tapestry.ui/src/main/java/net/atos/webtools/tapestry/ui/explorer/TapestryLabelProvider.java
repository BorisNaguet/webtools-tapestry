package net.atos.webtools.tapestry.ui.explorer;

import net.atos.webtools.tapestry.core.models.ProjectModel;
import net.atos.webtools.tapestry.core.models.features.AbstractFeatureModel;
import net.atos.webtools.tapestry.core.models.features.AssetModel;
import net.atos.webtools.tapestry.core.models.features.ComponentModel;
import net.atos.webtools.tapestry.core.models.features.MixinModel;
import net.atos.webtools.tapestry.core.models.features.PageModel;
import net.atos.webtools.tapestry.ui.TapestryUI;
import net.atos.webtools.tapestry.ui.explorer.TapestryContentProvider.Assets;
import net.atos.webtools.tapestry.ui.explorer.TapestryContentProvider.AssetsContainer;
import net.atos.webtools.tapestry.ui.explorer.TapestryContentProvider.ClasspathAssetsContainer;
import net.atos.webtools.tapestry.ui.explorer.TapestryContentProvider.ComponentsContainer;
import net.atos.webtools.tapestry.ui.explorer.TapestryContentProvider.MixinsContainer;
import net.atos.webtools.tapestry.ui.explorer.TapestryContentProvider.PagesContainer;
import net.atos.webtools.tapestry.ui.explorer.TapestryContentProvider.Property;
import net.atos.webtools.tapestry.ui.explorer.TapestryContentProvider.ServicesContainer;
import net.atos.webtools.tapestry.ui.util.UIConstants;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

public class TapestryLabelProvider extends BaseLabelProvider implements ILabelProvider {
	protected Image tapestryImage = TapestryUI.getDefault().getImage(UIConstants.IMG_TAPESTRY_DEFAULT);
	protected Image componentImage = TapestryUI.getDefault().getImage(UIConstants.IMG_TAPESTRY_COMPONENT);
	protected Image mixinImage = TapestryUI.getDefault().getImage(UIConstants.IMG_TAPESTRY_MIXIN);
	protected Image templateImage = TapestryUI.getDefault().getImage(UIConstants.IMG_TAPESTRY_TEMPLATE);
	protected Image propImage = TapestryUI.getDefault().getImage(UIConstants.IMG_TAPESTRY_PE);
	protected Image propertyImage = TapestryUI.getDefault().getImage("org.eclipse.ui.views.log", "icons/elcl16/properties.gif");
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof IProject) {
			return null;
		}
		else if(element instanceof Property) {
			return propertyImage;
		}
		if(element instanceof ComponentModel) {
			return componentImage;
		}
		if(element instanceof MixinModel) {
			return mixinImage;
		}
		if(element instanceof PageModel) {
			return templateImage;
		}
		if(element instanceof AssetsContainer || element instanceof ComponentsContainer || element instanceof MixinsContainer || element instanceof PagesContainer) {
			return tapestryImage;
		}
		return tapestryImage;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof IProject) {
			return null;
		}
		if(element instanceof ProjectModel) {
			ProjectModel projectModel = (ProjectModel) element;
			return "Tapestry (app: " + projectModel.getAppName() + ")";
		}
		if(element instanceof ComponentsContainer) {
			ComponentsContainer componentsContainer = (ComponentsContainer) element;
			return "Components (" + componentsContainer.collection.size() + ")";
		}
		if(element instanceof MixinsContainer) {
			MixinsContainer mixinsContainer = (MixinsContainer) element;
			return "Mixins (" + mixinsContainer.collection.size() + ")";
		}
		if(element instanceof PagesContainer) {
			PagesContainer pagesContainer = (PagesContainer) element;
			return "Templates (" + pagesContainer.collection.size() + ")";
		}
		if(element instanceof ServicesContainer) {
			ServicesContainer servicesContainer = (ServicesContainer) element;
			return "Services (" + servicesContainer.collection.size() + ")";
		}
		if(element instanceof Assets) {
			Assets assets = (Assets) element;
			return "Assets (" + (assets.assets.collection.size() + assets.classpathAssets.collection.size()) + ")";
		}
		if(element instanceof AssetsContainer) {
			AssetsContainer assets = (AssetsContainer) element;
			return "context: (" + assets.collection.size() + ")";
		}
		if(element instanceof ClasspathAssetsContainer) {
			ClasspathAssetsContainer classpathAssets = (ClasspathAssetsContainer) element;
			return "asset: (" + classpathAssets.collection.size() + ")";
		}
		if(element instanceof AbstractFeatureModel) {
			AbstractFeatureModel model = (AbstractFeatureModel) element;
			return model.getFullName();
		}
		if(element instanceof AssetModel) {
			AssetModel assetModel = (AssetModel) element;
			return assetModel.getName() + " : " + assetModel.getPath();
		}
		if(element instanceof Property) {
			Property property = (Property) element;
			return property.name + " : " + property.value;
		}
		return "_ERROR_";
	}

}
