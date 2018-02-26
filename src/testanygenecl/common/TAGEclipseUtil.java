package testanygenecl.common;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tag.common.PropertiesPool;

public class TAGEclipseUtil {
	private static TAGEclipseUtil instance;

	private TAGEclipseUtil() {
	}

	public static TAGEclipseUtil getInstance() {
		if (instance == null) {
			instance = new TAGEclipseUtil();
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
	public static boolean existTAGFolder(IProject project) {
		IFolder tagHomeFolder = project
				.getFolder(TAGEclipseConstants.FOLDERNAME_TAGHOME);
		if (!tagHomeFolder.exists()) {
			return false;
		}
		
		IFolder templateFolder = (IFolder) tagHomeFolder
				.getFolder(TAGEclipseConstants.FOLDERNAME_TEMPLATES);
		if (!templateFolder.exists()) {
			return false;
		}

		IFolder outputFolder = (IFolder) tagHomeFolder
				.getFolder(TAGEclipseConstants.FOLDERNAME_OUTPUT);
		if (!outputFolder.exists()) {
			return false;
		}
		return true;
	}

	public static void settingProperiesPool(IProject project){
//		IPreferenceStore store= Activator.getDefault().getPreferenceStore();
//		store.setDefault(TCGEclipseConstants.RESOURCE_PATH, resourcePath);
		IFolder tagHomeFolder = project
				.getFolder(TAGEclipseConstants.FOLDERNAME_TAGHOME);
		
		IFolder templateFolder = (IFolder) tagHomeFolder
				.getFolder(TAGEclipseConstants.FOLDERNAME_TEMPLATES);

		IFolder outputFolder = (IFolder) tagHomeFolder
				.getFolder(TAGEclipseConstants.FOLDERNAME_OUTPUT);
			
		PropertiesPool.setTemplatePath(templateFolder.getRawLocation().toString());
		PropertiesPool.setOutputPath(outputFolder.getRawLocation().toString());
		PropertiesPool.setTestGenerateLevel(1);//TODO <-- 디폴트 레벨

	}
	
	/**
	 * uitcgen 폴더를 현재 프로젝트 하위에 생성하는 메소드
	 * 
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public boolean addUITCGenFolder(IProject project) throws CoreException {
		// 1.기본 폴더 생성
		IFolder tagHomeFolder = project
				.getFolder(TAGEclipseConstants.FOLDERNAME_TAGHOME);
		if (!tagHomeFolder.exists()) {
			tagHomeFolder.create(true, true, new NullProgressMonitor());
		}
		
		IFolder templateFolder = (IFolder) tagHomeFolder
				.getFolder(TAGEclipseConstants.FOLDERNAME_TEMPLATES);
		if (!templateFolder.exists()) {
			templateFolder.create(true, true, new NullProgressMonitor());
		}

		IFolder outputFolder = (IFolder) tagHomeFolder
				.getFolder(TAGEclipseConstants.FOLDERNAME_OUTPUT);
		if (!outputFolder.exists()) {
			outputFolder.create(true, true, new NullProgressMonitor());
		}

		// 2.템플릿 파일 복사 (4개), 변수 돌려써도 될까? 따로따로 선언할까?
		//1.
		IFile toGenerateFile = (IFile) templateFolder
				.getFile(TAGEclipseConstants.FILENAME_TEMPLATEMAIN);
		InputStream toGenerateIS = this.getClass().getClassLoader().getResourceAsStream(TAGEclipseConstants.FILENAME_TEMPLATEMAIN);
		if (!toGenerateFile.exists()) {
			toGenerateFile.create(toGenerateIS, true, new NullProgressMonitor());
		}
		//2.
		toGenerateFile = (IFile) templateFolder
				.getFile(TAGEclipseConstants.FILENAME_TEMPLATEPOSITIVE);
		toGenerateIS = this.getClass().getClassLoader().getResourceAsStream(TAGEclipseConstants.FILENAME_TEMPLATEPOSITIVE);
		if (!toGenerateFile.exists()) {
			toGenerateFile.create(toGenerateIS, true, new NullProgressMonitor());
		}
		//3.		
		toGenerateFile = (IFile) templateFolder
				.getFile(TAGEclipseConstants.FILENAME_TEMPLATENEGATIVE);
		toGenerateIS = this.getClass().getClassLoader().getResourceAsStream(TAGEclipseConstants.FILENAME_TEMPLATENEGATIVE);
		if (!toGenerateFile.exists()) {
			toGenerateFile.create(toGenerateIS, true, new NullProgressMonitor());
		}
		//4.
		toGenerateFile = (IFile) templateFolder
				.getFile(TAGEclipseConstants.FILENAME_TESTDATAFACTORY);
		toGenerateIS = this.getClass().getClassLoader().getResourceAsStream(TAGEclipseConstants.FILENAME_TESTDATAFACTORY);
		if (!toGenerateFile.exists()) {
			toGenerateFile.create(toGenerateIS, true, new NullProgressMonitor());
		}
		settingProperiesPool(project);
		return true;
	}

}
