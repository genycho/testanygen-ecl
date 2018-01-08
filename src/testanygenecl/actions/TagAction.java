package testanygenecl.actions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tag.analyzer.RestAPISpecAnalyzer;
import com.tag.common.PropertiesPool;
import com.tag.restapi.spec.vo.RestAPIInfo;
import com.tag.restapi.writer.FreeMarkerGenerator;
import com.tag.restapi.writer.RestAPITestSuiteVO;
import com.tag.restapi.spec.parser.SwaggerSpecParser;

import io.swagger.models.Swagger;
import testanygenecl.common.EclipseUtil;
import testanygenecl.wizards.SpecInfoWizard;

public class TagAction implements
IObjectActionDelegate {

	private Shell shell;
	private IStructuredSelection selection;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		
	}

	public void run(IAction action) {
		IProject project = ((IResource) selection.getFirstElement()).getProject();
		MessageDialog.openInformation(shell, "UITCGen Dialog ", "Swagger 스펙 파일을 분석합니다");

		if (selection.getFirstElement() instanceof IFile) {
			IFile aFile = (IFile) selection.getFirstElement();
			if (!aFile.getName().endsWith(".json") && !aFile.getName().endsWith(".yaml")) {
				EclipseUtil.showInfoDialog(shell, "json 또는 yaml 파일이 아닙니다. 스펙 파일을 선택해 주세요 ");
				return;
			}
			SwaggerSpecParser parser = new SwaggerSpecParser();
			RestAPISpecAnalyzer analyzer = new RestAPISpecAnalyzer();
			FreeMarkerGenerator generator = new FreeMarkerGenerator();

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

				// 4. 완료 메시지
				String outputPath = PropertiesPool.getOutputPath();
				int generatedFileCount = 0;
				EclipseUtil.showInfoDialog(shell, "완료되었습니다.  " + outputPath+", - "+ generatedFileCount);
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
