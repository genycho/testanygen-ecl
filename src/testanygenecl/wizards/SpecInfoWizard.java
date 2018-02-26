package testanygenecl.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.Wizard;

import com.tag.analyzer.RestAPISpecAnalyzer;
import com.tag.common.PropertiesPool;
import com.tag.common.TAGException;
import com.tag.data.vo.TestCaseVO;
import com.tag.restapi.spec.vo.RestAPIInfo;
import com.tag.restapi.writer.FreeMarkerGenerator;
import com.tag.restapi.writer.PostmanWriter;
import com.tag.restapi.writer.RestAPITestCaseVO;
import com.tag.restapi.writer.RestAPITestSuiteVO;

import testanygenecl.common.EclipseUtil;

public class SpecInfoWizard extends Wizard{
	private IProject project = null;
	private List<RestAPIInfo> apiInfoList=null;
	private StringBuffer processingMessage = new StringBuffer();
	
	RestAPISpecAnalyzer analyzer = new RestAPISpecAnalyzer();
	FreeMarkerGenerator freeMarkerGenerator = new FreeMarkerGenerator();
	PostmanWriter postmanWriter = new PostmanWriter();
	SpecInfoPage specInfoPage;
//	AnalyzedTCsPage analyzedTCsPage;
	
	public SpecInfoWizard(){
		setWindowTitle("TestAnyGen - REST API ");
	}
	
	/**
	 * 생성 후 별도로 세팅할 메소드. 자동 호출되지 않으므로 임의로 호출할 것.
	 * @param eclipsePjtName
	 */
	public void init(IProject project, List<RestAPIInfo> apiInfoList){
		this.project = project;
		this.apiInfoList = apiInfoList;
	}
	
	public void addPages() {
		getShell().setSize(1000,600);
		addPage(new SpecInfoPage(apiInfoList));
//		addPage(new AnalyzedTCsPage());

		this.specInfoPage = (SpecInfoPage) getPage(SpecInfoPage.PAGE_NAME);
//		this.analyzedTCsPage = (AnalyzedTCsPage) getPage(AnalyzedTCsPage.PAGE_NAME);
	}
	
	@Override
	public boolean canFinish() {
		//HISTORY	체크박스 전체 체크하는 경우 아래 코드가 안 먹어서 Finish 초기에 최종 체크하는 걸로 변경? 
//		if(specInfoPage.getTableViewer().getCheckedElements() != null && specInfoPage.getTableViewer().getCheckedElements().length > 0) {
			return true;
//		}else {
//			return false;
//		}
	}
	
	@Override
	public boolean performFinish() {
		SpecInfoPage specInfoPage = (SpecInfoPage) getPage(SpecInfoPage.PAGE_NAME);
 		Object[] checkedItems = specInfoPage.getTableViewer().getCheckedElements();
 		int analyzeLevel = getAnalyzeLevel(specInfoPage.getAnalyzeLevelCB().getText());
 		String outputType = specInfoPage.getOutputTypeCB().getText();		
		
 		if(checkedItems == null || checkedItems.length <= 0) {
 			EclipseUtil.showInfoDialog(getShell(), "최소한 한 건이라도 선택해 주세요 ");
 			return false;
 		}
 		
		if(analyzeLevel > 0 && analyzeLevel <3) {
			PropertiesPool.setTestGenerateLevel(analyzeLevel);
		}else {
			EclipseUtil.showInfoDialog(getShell(), "Not (yet) supporting analyzing level- " + specInfoPage.getAnalyzeLevelCB().getText());
			return false;
		}
 		
		int successCount = 0;
		int analyzeFailCount = 0;
		int generateFailCount = 0;
		
		StringBuffer errorMsgStack = new StringBuffer();
			List<RestAPITestSuiteVO> testSuiteList = new ArrayList<>();
			for(Object current : checkedItems) {
				try {
					testSuiteList.add(analyzer.analyze((RestAPIInfo)current, 1));
				}catch(Exception skipForNext) {
					analyzeFailCount++;
					errorMsgStack.append("analyzing failed for ");
					try {
						errorMsgStack.append(((RestAPIInfo)current).getApiName());
					}catch(ClassCastException pass) {
						
					}
					errorMsgStack.append(", reason is ");
					errorMsgStack.append(skipForNext.getMessage());
					errorMsgStack.append("\n");
				}
			}
		
		if ("restassured".equals(outputType)) {
			for (RestAPITestSuiteVO current : testSuiteList) {
				try {
					freeMarkerGenerator.generateRestAssured(current);
					successCount++;
				} catch (Exception skipForNext) {
					generateFailCount++;
					errorMsgStack.append("generating failed for ");
					errorMsgStack.append(current.getName());
					errorMsgStack.append(", reason is ");
					errorMsgStack.append(skipForNext.getMessage());
					errorMsgStack.append("\n");
				}
			}
		} else if ("postman".equals(specInfoPage.getOutputTypeCB().getText())) {
			for (RestAPITestSuiteVO current : testSuiteList) {
				try {
					postmanWriter.generate(current);
					successCount++;
				} catch (Exception skipForNext) {
					generateFailCount++;
					errorMsgStack.append("generating failed for ");
					errorMsgStack.append(current.getName());
					errorMsgStack.append(", reason is ");
					errorMsgStack.append(skipForNext.getMessage());
					errorMsgStack.append("\n");
				}
			}
		} else {
			EclipseUtil.showInfoDialog(getShell(),
					"Not yet support output type - " + specInfoPage.getOutputTypeCB().getText());
			return false;
		}

		if (analyzeFailCount + generateFailCount <= 0) {
			EclipseUtil.showInfoDialog(getShell(), "Finished. generated count = " + successCount);
		}else {
			EclipseUtil.showInfoDialog(getShell(), "Failed. analyze fail = " + analyzeFailCount + ", generate fail = "
					+ generateFailCount + "successCount : " + successCount + "\n" + errorMsgStack.toString()+"\n");
		}
		return true;
	}
	
	/**
	 * 
	 * @param targetApiList
	 * @param testSuiteList
	 * @return	successCount
	 */
	@Deprecated//두번째 페이지 붙이고 동적으로 input 바꾸는게 잘 안 되어서 안 씀 
	protected int analyzeRestAPI() {
		List<Object> targetApiList = Arrays.asList(specInfoPage.getTableViewer().getCheckedElements());
		if(targetApiList == null || targetApiList.size() == 0) {
			return 0;
		}
		int successCount = 0;
		List<RestAPITestSuiteVO> testSuiteList = new ArrayList<>();
		for(Object restAPIInfo : targetApiList) {
			try {
				testSuiteList.add(analyzer.analyze((RestAPIInfo)restAPIInfo));
				successCount++;
			} catch (TAGException e) {
				processingMessage.append("(failCount++)Failed!! the reason is - ");
				processingMessage.append(e.getMessage());
				processingMessage.append("\n");
			}
		}
		List<TestCaseInfoViewVO> testCaseViewList = new ArrayList<>();
		//THINKME	여기서 분석을 하는게 맞을까? 아마도 선택 항목이 바뀌면 
		for(RestAPITestSuiteVO temp : testSuiteList) {
			if(temp.getTestCaseList() != null) {
				for(TestCaseVO temp2 : temp.getTestCaseList()) {
					try {
						testCaseViewList.add(new TestCaseInfoViewVO(temp, (RestAPITestCaseVO)temp2));
					}catch(Exception skip) {
						System.out.println("정말로 이 안으로 돌아온다고? 에이~설마.");
					}
				}
			}
		}
//		analyzedTCsPage.refreshInput(testCaseViewList);
		return successCount;
	}
	
	private int getAnalyzeLevel(String selectedComboText) {
		int analyzeLevel = -1;
		if (SpecInfoPage.CBCODE_LV1.equals(selectedComboText)) {
			analyzeLevel = 1;
		}else if(SpecInfoPage.CBCODE_LV2.equals(selectedComboText)) {
			analyzeLevel = 2;
		}else if(SpecInfoPage.CBCODE_LV3.equals(selectedComboText)) {
			analyzeLevel = 3;
		}else if(SpecInfoPage.CBCODE_LV4.equals(selectedComboText)) {
			analyzeLevel = 4;
		}else if(SpecInfoPage.CBCODE_LV5.equals(selectedComboText)) {
			analyzeLevel = 5;
		}
		return analyzeLevel;
	}

}
