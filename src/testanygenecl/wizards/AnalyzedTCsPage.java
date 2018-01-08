package testanygenecl.wizards;

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

import com.tag.restapi.spec.vo.RestAPIInfo;

public class AnalyzedTCsPage extends WizardPage{
	public static final String PAGE_NAME = "REST API TESTCASE LIST";
	
	private List<RestAPIInfo> restApiList;
	
	private TableViewer tableViewer;
	private Button button;
	
	public AnalyzedTCsPage(List<RestAPIInfo> restApiList){
		super(PAGE_NAME, "List of REST API TestCase list", null);
		this.restApiList = restApiList;
	}
	
	/**
	 * 최초에 control 생성 
	 */
	public void createControl(Composite parent){
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(1, false));
		
		tableViewer = new TableViewer(topLevel, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
				
		Table table = tableViewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH)); 
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    TableLayout layout = new TableLayout();
	    layout.addColumnData(new ColumnWeightData(10,100,true));//대상 API (이름), 
	    layout.addColumnData(new ColumnWeightData(20,100,true));//메소드
	    layout.addColumnData(new ColumnWeightData(10,100,true));//테스트 케이스명
	    layout.addColumnData(new ColumnWeightData(20,100,true));//설명
	    layout.addColumnData(new ColumnWeightData(30,100,true));//
	    layout.addColumnData(new ColumnWeightData(10,100,true));//
	    
	    TableColumn column1 = new TableColumn(table, SWT.CENTER);
	    column1.setWidth(100);
	    column1.setText("select");
	    TableColumn column2 = new TableColumn(table, SWT.CENTER);
	    column2.setWidth(100);
	    column2.setText("API Name");
	    TableColumn column3 = new TableColumn(table, SWT.CENTER);
	    column3.setWidth(100);
	    column3.setText("Method");
	    TableColumn column4 = new TableColumn(table, SWT.CENTER);
	    column4.setWidth(100);
	    column4.setText("ApiPath");
	    TableColumn column5 = new TableColumn(table, SWT.CENTER);
	    column5.setText("Description");
	    TableColumn column6 = new TableColumn(table, SWT.CENTER);
	    column6.setText("Category");
	    table.setLayout(layout);
	    
	    tableViewer.setContentProvider(new TagContentProvider());
	    tableViewer.setLabelProvider(new TagLabelProvider());

	    tableViewer.setInput(restApiList);
	    
		button = new Button(topLevel, SWT.BUTTON2);
//		button.add
		
//	    column1.addListener(SWT.Selection, sortListener);
//	    column2.addListener(SWT.Selection, sortListener);
//	    table.setSortColumn(column1);
//	    table.setSortDirection(SWT.UP);

	    
	    
//		boolean check = button.getSelection();

//		Label label = new Label(topLevel, SWT.CENTER);
//		label.setText("조회조건: 프로젝트 명 ");
//		
//		projectNameText = new Text(topLevel, SWT.SINGLE | SWT.BORDER);
//		projectNameText.setEnabled(true);
//		projectNameText.setTextLimit(255);
//		projectNameText.setFont(topLevel.getFont());
		
//		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
//      viewer.setContentProvider(new TreeContentProvider());
//      viewer.getTree().setHeaderVisible(true);
//      viewer.getTree().setLinesVisible(true);
//
//      TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
//      viewerColumn.getColumn().setWidth(300);
//      viewerColumn.getColumn().setText("Names");
//      viewerColumn.setLabelProvider(new ColumnLabelProvider());
//
//      
//
//      GridLayoutFactory.fillDefaults().generateLayout(parent);

		setControl(parent);
		setPageComplete(true);
	}
	
	public boolean useDefaultDirectory(){
		return button.getSelection();
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

class TestCaseInfoViewVO{
	RestAPIInfo restAPIInfo;
	public TestCaseInfoViewVO(RestAPIInfo restAPIInfo) {
		this.restAPIInfo = restAPIInfo;
	}
	
	public String[] getInformation() {
		String[] texts = new String[6];
		texts[0] = "false";
		texts[1] = restAPIInfo.getApiName();
		texts[2] = restAPIInfo.getMethod();
		texts[3] = restAPIInfo.getDescription();
		texts[4] = restAPIInfo.getApiPath();
		texts[5] = restAPIInfo.getApiTag();
		return texts;
	}
}

