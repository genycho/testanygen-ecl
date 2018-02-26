package testanygenecl.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.tag.data.vo.TestCaseVO;
import com.tag.restapi.spec.vo.RestAPIInfo;
import com.tag.restapi.writer.RestAPITestCaseVO;
import com.tag.restapi.writer.RestAPITestSuiteVO;

import testanygenecl.common.EclipseUtil;

public class AnalyzedTCsPage extends WizardPage{
	public static final String PAGE_NAME = "REST API TESTCASE LIST";
	
	private List<RestAPITestSuiteVO> restApiTestSuiteList;
	private List<TestCaseInfoViewVO> testCaseInfoViewVO = new ArrayList<>();
	boolean analyzed = false;
	
	private TableViewer tableViewer;
	
	public AnalyzedTCsPage(){
		super(PAGE_NAME, "List of REST API TestCase list", null);
	}
	
	public void analyze(List<RestAPITestSuiteVO> restApiTestSuiteList){
		this.restApiTestSuiteList = restApiTestSuiteList;
		//THINKME	여기서 분석을 하는게 맞을까? 아마도 선택 항목이 바뀌면 
		for(RestAPITestSuiteVO temp : restApiTestSuiteList) {
			if(temp.getTestCaseList() != null) {
				for(TestCaseVO temp2 : temp.getTestCaseList()) {
					try {
						this.testCaseInfoViewVO.add(new TestCaseInfoViewVO(temp, (RestAPITestCaseVO)temp2));
					}catch(Exception skip) {
						System.out.println("정말로 이 안으로 돌아온다고? 에이~설마.");
					}
				}
			}
		}
		analyzed = true;
	}
	
	
	
	/**
	 * 최초에 control 생성 
	 */
	public void createControl(Composite parent){
		if(analyzed == false) {
			EclipseUtil.showInfoDialog(getShell(), "분석이 사전에 실행되지 않았습니다.");
		}
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(1, false));
		
		Composite secondFirstLevel = new Composite(topLevel, SWT.NONE);
		Composite secondSecondLevel = new Composite(topLevel, SWT.NONE);
		
		secondFirstLevel.setLayout(new GridLayout(1, false));
		tableViewer = new TableViewer(secondFirstLevel, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
				
		Table table = tableViewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH)); 
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    TableLayout layout = new TableLayout();
	    layout.addColumnData(new ColumnWeightData(10,20,true));//no 
	    layout.addColumnData(new ColumnWeightData(20,40,true));//대상 이름
	    layout.addColumnData(new ColumnWeightData(10,50,true));//테스트 케이스명
	    layout.addColumnData(new ColumnWeightData(20,40,true));//타입
	    layout.addColumnData(new ColumnWeightData(30,200,true));//설명
	    layout.addColumnData(new ColumnWeightData(10,20,true));//분석레벨
	    
	    TableColumn column1 = new TableColumn(table, SWT.CENTER);
	    column1.setText("no");
	    TableColumn column2 = new TableColumn(table, SWT.CENTER);
	    column2.setText("Test Suite");
	    TableColumn column3 = new TableColumn(table, SWT.CENTER);
	    column3.setText("TC Name");
	    TableColumn column4 = new TableColumn(table, SWT.CENTER);
	    column4.setText("Type");
	    TableColumn column5 = new TableColumn(table, SWT.CENTER);
	    column5.setText("Description");
	    TableColumn column6 = new TableColumn(table, SWT.CENTER);
	    column6.setText("Level");
	    table.setLayout(layout);
	    
	    tableViewer.setContentProvider(new TagContentProvider());
	    tableViewer.setLabelProvider(new TagLabelProvider());

	    tableViewer.setInput(restApiTestSuiteList);
	    
	    secondSecondLevel.setLayout(new GridLayout(2, false));
	    
		setControl(parent);
		setPageComplete(true);
	}

	public void refreshInput(List<TestCaseInfoViewVO> testCaseViewList) {
		tableViewer.setInput(testCaseViewList);
		tableViewer.refresh();
	}

}

class TagRATCContentProvider implements IStructuredContentProvider{
	@Override
	public Object[] getElements(Object arg0) {
//		return new RestAPIInfoViewVO((RestAPIInfo) arg0).getInformation();
		List<RestAPIInfo> itemList = (List<RestAPIInfo>) arg0; 
		return itemList.toArray();
	}
}

class TagRATCLabelProvider implements ITableLabelProvider{
	int rowCountHereQuestion = 0;
	@Override
	public void addListener(ILabelProviderListener arg0) {
	}
	@Override
	public void dispose() {
	}
	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}
	@Override
	public void removeListener(ILabelProviderListener arg0) {
	}
	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		return null;
	}
	@Override
	public String getColumnText(Object arg0, int arg1) {
		if(arg0 == null) {
			return null;
		}
		
		TestCaseInfoViewVO testCaseInfoViewVO = (TestCaseInfoViewVO) arg0;
		switch(arg1) {
		case 0 :
			return String.valueOf(++rowCountHereQuestion);
		case 1 :
			return testCaseInfoViewVO.getTargetName();
		case 2 :
			return testCaseInfoViewVO.getTcName();
		case 3 :
			return testCaseInfoViewVO.getTcType();
		case 4 :
			return testCaseInfoViewVO.getDescription();
		case 5 :
			return testCaseInfoViewVO.getAnalyzeLevel();
		default:
			return "NotExpectedIndex!";
		}
	}
}

class TestCaseInfoViewVO{
	String tcName;
	String targetName;
	String tcType;
	String description;
	String analyzeLevel;
	
	RestAPITestSuiteVO testSuiteInfo;
	RestAPITestCaseVO testCaseInfo;
	
	public TestCaseInfoViewVO(RestAPITestSuiteVO testSuiteInfo, RestAPITestCaseVO testCaseInfo) {
		this.testSuiteInfo = testSuiteInfo;
		this.testCaseInfo = testCaseInfo;
		
		this.targetName = testSuiteInfo.getTargetName() + "("+testSuiteInfo.getMethodType()+")";
		this.tcName = testCaseInfo.getName();
		this.description = testCaseInfo.getDescription();
		this.analyzeLevel = String.valueOf(testCaseInfo.getGeneratedTestLevel());
		this.tcType = testCaseInfo.getTestCaseType();
	}

	public String getTcName() {
		return tcName;
	}
	public void setTcName(String tcName) {
		this.tcName = tcName;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTcType() {
		return tcType;
	}
	public void setTcType(String tcType) {
		this.tcType = tcType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnalyzeLevel() {
		return analyzeLevel;
	}
	public void setAnalyzeLevel(String analyzeLevel) {
		this.analyzeLevel = analyzeLevel;
	}
	public RestAPITestSuiteVO getTestSuiteInfo() {
		return testSuiteInfo;
	}
	public RestAPITestCaseVO getTestCaseInfo() {
		return testCaseInfo;
	}
}

