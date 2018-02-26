package testanygenecl.actions;

import java.util.List;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tag.restapi.spec.parser.SwaggerSpecParser;

import io.swagger.models.Swagger;
import testanygenecl.common.EclipseUtil;
import testanygenecl.common.TAGEclipseUtil;
import testanygenecl.wizards.SpecInfoWizard;

public class TagAction implements
IObjectActionDelegate {
	private Shell shell;
	private IStructuredSelection selection;
	
	SwaggerSpecParser parser = new SwaggerSpecParser();
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		IProject project = ((IResource) selection.getFirstElement()).getProject();
		
		try {
			TAGEclipseUtil.getInstance().addUITCGenFolder(project);
			TAGEclipseUtil.settingProperiesPool(project);
		} catch (CoreException e) {
			EclipseUtil.showErrorDialog(shell, "TAG 관련 폴더와 리소스를 생성하는 과정에서 오류가 발생하였습니다 - " + e.getMessage(), e);
		}
		
		if (selection.getFirstElement() instanceof IFile) {
			IFile aFile = (IFile) selection.getFirstElement();
			if (!aFile.getName().endsWith(".json") && !aFile.getName().endsWith(".yaml")) {
				EclipseUtil.showInfoDialog(shell, "json 또는 yaml 파일이 아닙니다. 스펙 파일을 선택해 주세요 ");
				return;
			}
			String filePath = aFile.getLocation().toString();
			// 1) 스펙 파일 분석
			try {
				Swagger swaggerInfo = parser.parseSpecFile(filePath);
				List<com.tag.restapi.spec.vo.RestAPIInfo> restAPIInfoList = parser.parse(swaggerInfo);

				// 2) 정보 표시 페이지 띄움
				SpecInfoWizard wizard = new SpecInfoWizard();
				wizard.init(project, restAPIInfoList);

				WizardDialog wizardDialog = new WizardDialog(shell, wizard);
				wizardDialog.create();
				wizardDialog.open();

				try {
					project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				} catch (CoreException ignore) {
				}
			} catch (Exception e) {
				EclipseUtil.showErrorDialog(shell, "중단 되었습니다. ", e);
			}
		} else {
			EclipseUtil.showInfoDialog(shell, "OpenAPI 스펙파일(.json or .yaml)을 선택해 주세요.  ");
			return;
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

}
