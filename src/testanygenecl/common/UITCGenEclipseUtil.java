package testanygenecl.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;

//import com.sds.uitcgen.common.PropertiesPool;
//import com.sds.uitcgen.common.TCGenConstants;

import testanygenecl.Activator;

public class UITCGenEclipseUtil {
	private static UITCGenEclipseUtil instance;

	private UITCGenEclipseUtil() {
	}

	public static UITCGenEclipseUtil getInstance() {
		if (instance == null) {
			instance = new UITCGenEclipseUtil();
		}
		return instance;
	}

	/**
	 * 입력받은 프로젝트에 uitcgen 폴더가 존재하는지 체크하는 메소드<br/>
	 * uitcgen이 존재하는 경우 PropertiesPool에 resource path, excel path, excel file
	 * name, uxml path를 세팅한다.
	 * 
	 * @param project
	 * @return
	 */
	public static boolean existUITCGenFolder(IProject project) {
		IFolder uitcgenFolder = project
				.getFolder(TCGEclipseConstants.UITCGEN_FOLDERNAME);
		if (!uitcgenFolder.exists()) {
			return false;
		}
		IFolder excelFolder = (IFolder) uitcgenFolder
				.getFolder(TCGEclipseConstants.EXCEL_FOLDERNAME);
		if (!excelFolder.exists()) {
			return false;
		}

		IFolder uxmlsFolder = (IFolder) uitcgenFolder
				.getFolder(TCGEclipseConstants.UXMLS_FOLDERNAME);
		if (!uxmlsFolder.exists()) {
			return false;
		}

		settingProperiesPool(project);
		
		return true;
	}

	private static void settingProperiesPool(IProject project){
		IPreferenceStore store= Activator.getDefault().getPreferenceStore();
		
//		store.setDefault(TCGEclipseConstants.RESOURCE_PATH, resourcePath);
		
//		PropertiesPool.setResourceFolderPath(getUITCGenPath(project));
//		PropertiesPool.setExcelOutputPath(getUXMLPath(project));
//		PropertiesPool.setExcelFileName(TCGenConstants.EXCEL_FILENAME);
//		PropertiesPool.setUxmlPath(getUXMLPath(project));
//		
//		PropertiesPool.setUiSelectOption(store.getString(TCGEclipseConstants.UI_SELECT_OPTION_TEXT));
//		PropertiesPool.setUxmlSelectOption(store.getString(TCGEclipseConstants.UXML_SELECT_OPTION_TEXT));
//		
//		PropertiesPool.setExportType(store.getString(TCGEclipseConstants.EXPORT_TYPE_TEXT));
//		PropertiesPool.setUiRoot(uiRoot); TODO
				
	}
	
	/**
	 * uitcgen 폴더를 현재 프로젝트 하위에 생성하는 메소드
	 * 
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public boolean addUITCGenFolder(IProject project) throws CoreException {

		// 1.
//		IFolder uitcgenFolder = (IFolder) project
//				.getFolder(TCGEclipseConstants.UITCGEN_FOLDERNAME);
//		uitcgenFolder.create(true, true, new NullProgressMonitor());
//
//		IFolder excelFolder = (IFolder) uitcgenFolder
//				.getFolder(TCGEclipseConstants.EXCEL_FOLDERNAME);
//		IFolder uxmlsFolder = (IFolder) uitcgenFolder
//				.getFolder(TCGEclipseConstants.UXMLS_FOLDERNAME);
//
//		excelFolder.create(true, true, new NullProgressMonitor());
//		uxmlsFolder.create(true, true, new NullProgressMonitor());

		// 2.룰 파일 복사 
//		IFile ruleFile = (IFile) uitcgenFolder
//				.getFile(TCGenConstants.RULE_FILENAME);
//		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
//				TCGenConstants.RULE_FILENAME);
//		ruleFile.create(is, true, new NullProgressMonitor());
//		
//		//3. 테스트 시나리오 템플릿 파일 복사 
//		IFile scenarioTemplateFile = (IFile) uitcgenFolder
//		.getFile(TCGenConstants.UNITSCENARIOTEMPLATE);
//		InputStream templateIS = this.getClass().getClassLoader()
//				.getResourceAsStream(TCGenConstants.UNITSCENARIOTEMPLATE);
//		scenarioTemplateFile
//				.create(templateIS, true, new NullProgressMonitor());

		settingProperiesPool(project);

		return true;
	}

	/**
	 * 프로젝트 하위의 uxml 폴더 경로를 찾아 반환한다. </br> 존재하지 않는 경우 null 반환
	 * 
	 * @param project
	 * @return
	 */
	public static String getUITCGenPath(IProject project) {
		return project.getFolder(TCGEclipseConstants.UITCGEN_FOLDERNAME)
				.getRawLocation().toString();
	}

	/**
	 * 프로젝트 하위의 uxml 폴더 경로를 찾아 반환한다. </br> 존재하지 않는 경우 null 반환
	 * 
	 * @param project
	 * @return
	 */
	public static String getUXMLPath(IProject project) {
		IFolder uitcgenFolder = project
				.getFolder(TCGEclipseConstants.UITCGEN_FOLDERNAME);

		return uitcgenFolder.getFolder(TCGEclipseConstants.UXMLS_FOLDERNAME)
				.getRawLocation().toString();
	}

	/**
	 * 
	 * @param project
	 * @return
	 */
	public static String getExcelPath(IProject project) {
		IFolder uitcgenFolder = project
				.getFolder(TCGEclipseConstants.UITCGEN_FOLDERNAME);
		return uitcgenFolder.getFolder(TCGEclipseConstants.EXCEL_FOLDERNAME)
				.getRawLocation().toString();
	}
}
