package net.atos.webtools.tapestry.ui.explorer;

import java.util.Collection;

import net.atos.webtools.tapestry.core.TapestryCore;
import net.atos.webtools.tapestry.core.models.ProjectModel;
import net.atos.webtools.tapestry.core.models.features.AbstractFeatureModel;
import net.atos.webtools.tapestry.core.models.features.AssetModel;
import net.atos.webtools.tapestry.core.models.features.ComponentModel;
import net.atos.webtools.tapestry.core.models.features.MixinModel;
import net.atos.webtools.tapestry.core.models.features.PageModel;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

public class TapestryContentProvider extends BaseWorkbenchContentProvider implements ITreeContentProvider {

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IProject) {
			return getElements(parentElement);
		}
		else if (parentElement instanceof ProjectModel) {
			ProjectModel projectModel = (ProjectModel) parentElement;
			return new Object[]{
					new Property("Name", projectModel.getAppName()),
					new Property("AppModule", projectModel.getAppModule()),
					new Property("Package", projectModel.getAppPackage()),
					new ComponentContainer(projectModel.getComponents()),
					new PagesContainer(projectModel.getPages()),
					new MixinContainer(projectModel.getMixins()),
					new AssetsContainer(projectModel.getAssets())
			};
		}
		else if (parentElement instanceof ListContainer) {
			return ((ListContainer<?>) parentElement).collection.toArray();
		}
		else if(parentElement instanceof AbstractFeatureModel) {
			AbstractFeatureModel abstractFeatureModel = (AbstractFeatureModel) parentElement;
			return new Property[]{
				new Property("prefix", abstractFeatureModel.getPrefix()),
				new Property("doc", abstractFeatureModel.getDoc()),
				new Property("src", abstractFeatureModel.getSource()),
				new Property("name", abstractFeatureModel.getName()),
			};
		}
		return new Object[0];
	}

	@Override
	public Object[] getElements(Object element) {
		if (element instanceof IProject) {
			ProjectModel projectModel = TapestryCore.getDefault().getProjectModel(((IProject) element), true);
			if(projectModel != null && projectModel.getAppModule() != null) {
				return new ProjectModel[]{projectModel};
			}
		}
		return new Object[0];
	}
	
	public class Property{
		String value;
		String name;

		public Property(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}
	
	abstract class ListContainer<T> {
		Collection<T> collection;
	}
	
	public class ComponentContainer extends ListContainer<ComponentModel> {
		ComponentContainer(Collection<ComponentModel> components){
			this.collection = components;
		}
	}
	
	public class PagesContainer extends ListContainer<PageModel> {
		PagesContainer(Collection<PageModel> pages){
			this.collection = pages;
		}
	}
	
	public class MixinContainer extends ListContainer<MixinModel> {
		MixinContainer(Collection<MixinModel> mixins){
			this.collection = mixins;
		}
	}
	
	public class AssetsContainer extends ListContainer<AssetModel> {
		AssetsContainer(Collection<AssetModel> assets){
			this.collection = assets;
		}
	}
}