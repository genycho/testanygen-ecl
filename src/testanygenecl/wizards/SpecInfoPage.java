package testanygenecl.wizards;

import java.util.List;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.tag.restapi.spec.vo.RestAPIInfo;

import testanygenecl.common.EclipseUtil;

public class SpecInfoPage extends WizardPage{
	public static final String PAGE_NAME = "REST API SPECIFINCATION INFO";
	
	public static final String CBCODE_LV1 = "Lv1 (default)";
	public static final String CBCODE_LV2 = "Lv2 (responses)";
	public static final String CBCODE_LV3 = "Lv3 (method)";
	public static final String CBCODE_LV4 = "Lv4 (parameters)";
	public static final String CBCODE_LV5 = "Lv5 (advanced)";
	
	private List<RestAPIInfo> restApiList;
	
	private CheckboxTableViewer tableViewer;
	
	private Button previewButton;
	private Button generateButton;
	private Combo analyzeLevelCB;
	private Combo outputTypeCB;
	private Button allCheckButton;
	
	public SpecInfoPage(List<RestAPIInfo> restApiList){
		super(PAGE_NAME, PAGE_NAME, null);
		this.restApiList = restApiList;
	}
	
	@Override
	public Image getImage() {
		return null;
	}

	/**
	 * 최초에 control 생성 
	 */
	public void createControl(Composite parent){
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(1, false));
		parent.setSize(820, 380);
		topLevel.setSize(820, 380);
		
		Composite secondFirstLevel = new Composite(topLevel, SWT.NONE);
		secondFirstLevel.setLayout(EclipseUtil.getDefaultGridLayout(getShell(), 4));
		
		Composite secondSecondLevel = new Composite(topLevel, SWT.NONE);
		secondSecondLevel.setLayout(EclipseUtil.getDefaultGridLayout(getShell(), 2,false));
		
		Composite secondThirdLevel = new Composite(topLevel, SWT.NONE);
		secondThirdLevel.setLayout(EclipseUtil.getDefaultGridLayout(getShell(), 1,false));
		
		//TODO	향후 구현할 기능 
//	    previewButton = new Button(secondFirstLevel, SWT.PUSH);
//	    previewButton.setText("테스트 케이스 목록 미리보기");
//	    previewButton.addListener(SWT.Selection, new Listener() {
//			@Override
//			public void handleEvent(Event e) {
//				switch (e.type) {
//		          case SWT.Selection:
//		        	  EclipseUtil.showInfoDialog(getShell(), "준비중입니다.");
//		            break;
//		          }
//			}
//	      });
//		generateButton = new Button(secondFirstLevel, SWT.PUSH);
//		generateButton.setText("바로 생성하기");
//		generateButton.addListener(SWT.Selection, new Listener() {
//			@Override
//			public void handleEvent(Event e) {
//				switch (e.type) {
//				case SWT.Selection:
//					EclipseUtil.showInfoDialog(getShell(), "준비중입니다.Finish 버튼으로 생성해 주세요.");
//					break;
//				}
//			}
//		});
		
		/* 1-1) Second Component */
		EclipseUtil.getLabel(secondFirstLevel, "Test Analyze Level Selection: ");
		
		/* 1-2) Third Component */
		this.analyzeLevelCB = new Combo(secondFirstLevel, SWT.READ_ONLY);
		analyzeLevelCB.setBounds(50, 50, 150, 65);
	    String levelItems[] = { CBCODE_LV1, CBCODE_LV2, CBCODE_LV3, CBCODE_LV4, CBCODE_LV5};
	    analyzeLevelCB.setItems(levelItems);
	    analyzeLevelCB.select(0);
		
	    /* 1-3) Fourth Component */
	    EclipseUtil.getLabel(secondFirstLevel, "Output Type Selection: ");
		
		/* 1-4) Fifth Component */
	    outputTypeCB = new Combo(secondFirstLevel, SWT.READ_ONLY);
	    outputTypeCB.setBounds(50, 50, 150, 65);
	    String outputTypeItems[] = { "restassured", "postman","documents"};
	    outputTypeCB.setItems(outputTypeItems);
	    outputTypeCB.select(0);
	    
		/* 2-1) First Component */
		allCheckButton = new Button(secondSecondLevel, SWT.CHECK);
		allCheckButton.setText("all");
		allCheckButton.addListener(SWT.CHECK, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.CHECK:
					tableViewer.setAllChecked(allCheckButton.getSelection());
					break;
				}
			}
		});
		
		//3-1)
		tableViewer = CheckboxTableViewer.newCheckList(secondThirdLevel, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		Table table = tableViewer.getTable();
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
		GridData tableGridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 9,	1);
		tableGridData.widthHint = 800;
		tableGridData.heightHint = 300;
		table.setLayoutData(tableGridData);
//		table.setSize(800, 200);
		
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent arg0) {
				Object[] checkedElements = tableViewer.getCheckedElements();
				if (checkedElements != null && checkedElements.length > 0) {
//					generateButton.setEnabled(true);
					setPageComplete(true);
				} else {
//					generateButton.setEnabled(false);
					setPageComplete(false);
				}
			}
		});
		
	    TableLayout layout = new TableLayout();
	    layout.addColumnData(new ColumnWeightData(10,10,true));//체크박스?
	    layout.addColumnData(new ColumnWeightData(20,80,true));//이름
	    layout.addColumnData(new ColumnWeightData(10,30,true));//메소드
	    layout.addColumnData(new ColumnWeightData(20,40,true));//경로
	    layout.addColumnData(new ColumnWeightData(30,200,true));//설명
	    layout.addColumnData(new ColumnWeightData(10,60,true));//그룹
	    
	    TableColumn column1 = new TableColumn(table, SWT.CENTER);
//	    column1.setWidth(100);
	    column1.setText("sel");
	    TableColumn column2 = new TableColumn(table, SWT.CENTER);
//	    column2.setWidth(100);
	    column2.setText("API Name");
	    TableColumn column3 = new TableColumn(table, SWT.CENTER);
//	    column3.setWidth(100);
	    column3.setText("Method");
	    TableColumn column4 = new TableColumn(table, SWT.CENTER);
//	    column4.setWidth(100);
	    column4.setText("ApiPath");
	    TableColumn column5 = new TableColumn(table, SWT.CENTER);
	    column5.setText("Description");
	    TableColumn column6 = new TableColumn(table, SWT.CENTER);
	    column6.setText("Category");
	    table.setLayout(layout);
	    
	    tableViewer.setContentProvider(new TagContentProvider());
	    tableViewer.setLabelProvider(new TagLabelProvider());

	    tableViewer.setInput(restApiList);
		
//	    column1.addListener(SWT.Selection, sortListener);
//	    column2.addListener(SWT.Selection, sortListener);
//	    table.setSortColumn(column1);
//	    table.setSortDirection(SWT.UP);
//		boolean check = button.getSelection();
//		Label label = new Label(topLevel, SWT.CENTER);
//		label.setText("조회조건: 프로젝트 명 ");

		setControl(parent);
//		setPageComplete(true);
	}
	
//	public IWizardPage getNextPage() {
//		EclipseUtil.showInfoDialog(getShell(), "언제 호출되는지 확인");
		//Next 버튼 누르는 시점에 선택된 체크 항목에 대해 테스트 케이스 분석 후 다음 페이지 테이블 리프레시 수행
//		((SpecInfoWizard)getWizard()).analyzeRestAPI();
//		return super.getNextPage();
//	}

	public CheckboxTableViewer getTableViewer() {
		return tableViewer;
	}

	public Combo getOutputTypeCB() {
		return outputTypeCB;
	}

	public Button getPreviewButton() {
		return previewButton;
	}

	public Combo getAnalyzeLevelCB() {
		return analyzeLevelCB;
	}

	public Button getAllCheckButton() {
		return allCheckButton;
	}
}

class TagContentProvider implements IStructuredContentProvider{
	@Override
	public Object[] getElements(Object arg0) {
		List<RestAPIInfo> itemList = (List<RestAPIInfo>) arg0; 
		return itemList.toArray();
	}
}

class TagLabelProvider implements ITableLabelProvider{
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
		RestAPIInfo restAPIInfo = (RestAPIInfo) arg0;
		switch(arg1) {
		case 0 :
			return "";			
		case 1 :
			return restAPIInfo.getApiName();
		case 2 :
			return restAPIInfo.getMethod();
		case 3 :
			return restAPIInfo.getApiPath();
		case 4 :
			return restAPIInfo.getDescription();
		case 5 :
			return restAPIInfo.getApiTag();
		default:
			return "NotExpectedIndex!";
		}
		
	}
}