package testanygenecl.wizards;

import java.util.List;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.tag.restapi.spec.vo.RestAPIInfo;

public class SpecInfoPage extends WizardPage{
	public static final String PAGE_NAME = "REST API SPECIFINCATION INFO";
	
	private List<RestAPIInfo> restApiList;
	
	private CheckboxTableViewer tableViewer;
	private Button generateButton;
	private Text searchText;
	private Combo outputTypeCB;
	
	public SpecInfoPage(List<RestAPIInfo> restApiList){
		super(PAGE_NAME, "Information of Specification file", null);
		this.restApiList = restApiList;
	}
	
	/**
	 * 최초에 control 생성 
	 */
	public void createControl(Composite parent){
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(1, false));
		
		tableViewer = CheckboxTableViewer.newCheckList(topLevel, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

				
		Table table = tableViewer.getTable();
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
		GridData tableGridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 9,	1);
		tableGridData.heightHint = 240;
		tableGridData.widthHint = 1000;
		table.setLayoutData(tableGridData);
		
	    TableLayout layout = new TableLayout();
	    layout.addColumnData(new ColumnWeightData(10,50,true));//체크박스?
	    layout.addColumnData(new ColumnWeightData(20,100,true));//이름
	    layout.addColumnData(new ColumnWeightData(10,50,true));//메소드
	    layout.addColumnData(new ColumnWeightData(20,60,true));//경로
	    layout.addColumnData(new ColumnWeightData(30,200,true));//설명
	    layout.addColumnData(new ColumnWeightData(10,80,true));//그룹
	    
	    TableColumn column1 = new TableColumn(table, SWT.CENTER);
//	    column1.setWidth(100);
	    column1.setText("select");
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
	    
	    outputTypeCB = new Combo(topLevel, SWT.READ_ONLY);
	    outputTypeCB.setBounds(50, 50, 150, 65);
	    String items[] = { "restassured", "PostMan",".csv"};
	    outputTypeCB.setItems(items);
	    outputTypeCB.select(0);
//	    outputTypeCB.setEnabled(false);//선택된 목록이 0개일때는 비활성화
	    
	    
		generateButton = new Button(topLevel, SWT.PUSH);
		generateButton.setText("바로 생성하기");
//		generateButton.setEnabled(false);//선택된 목록이 0개일때는 비활성화
		
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
//      GridLayoutFactory.fillDefaults().generateLayout(parent);
		setControl(parent);
		setPageComplete(true);
	}
	
	public boolean useDefaultDirectory(){
		return generateButton.getSelection();
	}

}

class TagContentProvider implements IStructuredContentProvider{
	@Override
	public Object[] getElements(Object arg0) {
//		return new RestAPIInfoViewVO((RestAPIInfo) arg0).getInformation();
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

//class RestAPIInfoViewVO{
//	RestAPIInfo restAPIInfo;
//	public RestAPIInfoViewVO(RestAPIInfo restAPIInfo) {
//		this.restAPIInfo = restAPIInfo;
//	}
//	
//	public String[] getInformation() {
//		String[] texts = new String[6];
//		texts[0] = "false";
//		texts[1] = restAPIInfo.getApiName();
//		texts[2] = restAPIInfo.getMethod();
//		texts[3] = restAPIInfo.getDescription();
//		texts[4] = restAPIInfo.getApiPath();
//		texts[5] = restAPIInfo.getApiTag();
//		
//		return texts;
//	}
//}