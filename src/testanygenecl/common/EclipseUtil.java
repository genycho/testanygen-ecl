package testanygenecl.common;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import testanygenecl.Activator;

public class EclipseUtil {
	protected EclipseUtil() {
		throw new UnsupportedOperationException();
	}

	
	public static void showErrorDialog(Shell shell, final String exceptionMsg,
			final Throwable exception) {
		MessageDialog.openError(shell, "UITCGen Dialog ", exceptionMsg);

		IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
				exceptionMsg, exception);
		Activator.getDefault().getLog().log(status);
	}
	
	public static void showInfoDialog(Shell shell, String infoMsg) {
		 MessageDialog
         .openInformation(
        		 shell,
                 "UITCGen Dialog ", 
                 infoMsg);
	}
	
	/**
	 * 이클립스에서 에러 창 표시 
	 * @param exceptionMsg
	 * @param exceptionType
	 * @param exception
	 */
	public static void showException(final String exceptionMsg,
			final int exceptionType, final Throwable exception) {
		IStatus status = new Status(exceptionType, Activator.PLUGIN_ID,
				exceptionMsg, exception);
		Activator.getDefault().getLog().log(status);
	}
	
	public static void showException(final String exceptionMsg,
			final int exceptionType) {
		IStatus status = new Status(exceptionType, Activator.PLUGIN_ID,
				exceptionMsg);
		Activator.getDefault().getLog().log(status);
	}
	
	public static IProject findProject(String projectName) {   
        IWorkspace workspace = ResourcesPlugin.getWorkspace();   
        IProject[] projectlist = workspace.getRoot().getProjects();   
   
        IProject project = null;   
        for (int i = 0; i < projectlist.length; i++) {   
            if (projectlist[i].getName().equals(projectName)) {   
                project = projectlist[i];   
            }   
        }   
        return project;   
    }   
   
    public static void refreshProject(String projectName) {   
        try {   
            IProject project = findProject(projectName);   
            project.refreshLocal(IResource.DEPTH_INFINITE, null);   
   
            // IWorkspace workspace =   
            // ResourcesPlugin.getWorkspace();   
            // workspace.getRoot().getProject(projectName)   
            // .refreshLocal(IResource.DEPTH_INFINITE,   
            // null);   
        } catch (CoreException e) {
			EclipseUtil.showException("refresh error", IStatus.ERROR, e);
		}   
    }   
   
    public static IResource getSelectedResource(ISelection selection) {   
        ArrayList<Object> resources = null;   
        if (!selection.isEmpty()) {   
            resources = new ArrayList<Object>();   
            if (selection instanceof IStructuredSelection) {   
                Iterator<?> elements =   
                    ((IStructuredSelection) selection).iterator();   
                while (elements.hasNext()) {   
                    Object next = elements.next();   
                    if (next instanceof IResource) {   
                        resources.add(next);   
                        continue;   
                    }   
                    if (next instanceof IAdaptable) {   
                        IAdaptable adaptable = (IAdaptable) next;   
                        Object adapter = adaptable.getAdapter(IResource.class);   
                        if (adapter instanceof IResource) {   
                            resources.add(adapter);   
                            continue;   
                        }   
                    }   
                }   
            }   
        }   
   
        if (resources != null && !resources.isEmpty()) {   
            IResource[] result = new IResource[resources.size()];   
            resources.toArray(result);   
            if (result.length >= 1)   
                return result[0];   
        }   
        return null;   
    }   
   
}
