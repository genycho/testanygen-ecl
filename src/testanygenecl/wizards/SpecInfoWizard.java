package testanygenecl.wizards;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.Wizard;

import com.tag.restapi.spec.vo.RestAPIInfo;

import testanygenecl.common.EclipseUtil;

public class SpecInfoWizard extends Wizard{
	private IProject project = null;
	private List<RestAPIInfo> apiInfoList=null;
	
	/**
	 * 생성 후 별도로 세팅할 메소드. 자동 호출되지 않으므로 임의로 호출할 것.
	 * @param eclipsePjtName
	 */
	public void init(IProject project, List<RestAPIInfo> apiInfoList){
		this.project = project;
		this.apiInfoList = apiInfoList;
	}
	
	public void addPages(){
		addPage(new SpecInfoPage(apiInfoList));
	}
	
//	private SpecInfoPage getDirectoryPage(){
//		return (SpecInfoPage) getPage(SpecInfoPage.PAGE_NAME);
//	}
	
//	public boolean performCancel(){
//		System.out.println("Perform Cancel called");
//		return true;
//	}
	
//	public IWizardPage getNextPage(IWizardPage page){
//		ExportPage dirPage = (ExportPage) page;
//		if(page instanceof DirectoryPage){
//			DirectoryPage dirPage = (DirectoryPage) page;
//			if(dirPage.useDefaultDirectory()){
//				System.out.println("");
//			}
//		}
//		return dirPage;
//	}
	
	@Override
	public boolean performFinish() {
//		ExportPage directoryPage = getDirectoryPage();
//		if(directoryPage.useDefaultDirectory()){
//			System.out.println("Use default. !!");
//		}
//		this.eclipsePjtName = getDirectoryPage().getEclipsePjtName();
		int successCount = 0;
		try{
			//
		}catch(Exception e){
			EclipseUtil.showErrorDialog(getShell(),
					"테스트 결과 파일 생성 과정에서 에러가 발생하였습니다. " + e.getMessage(), e);
		}
		
		if(successCount == 0){
			EclipseUtil.showInfoDialog(getShell(), "No Data to Export!!! \n\n해당하는 테스트 케이스가 존재하지 않습니다.");
		}else{
//			EclipseUtil.showInfoDialog(getShell(), successCount
//					+ " 건의 테스트 케이스가 Export 되었습니다. 엑셀 출력 경로 : "
//					+ PropertiesPool.getExcelOutputPath());
		}
		return true;
	}

	/**
	 * type :: value format <br/>
	 * EXPORT_CONDITION_PROJECTNAME ::: String<br/>
	 * EXPORT_CONDITION_COMPONENTNAME ::: String<br/>
	 * EXPORT_CONDITION_SCREENID ::: List<String> <br/>
	 * @return
	 */
	public Object getSelectedKeywords(){
		return null;
	}

}
