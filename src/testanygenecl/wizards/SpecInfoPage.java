package testanygenecl.wizards;

import java.util.List;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import io.swagger.models.Swagger;

public class SpecInfoPage extends WizardPage{
	public static final String PAGE_NAME = "REST API SPECIFINCATION INFO";
	
	private Button button;
	private Text projectNameText;
	
	private TreeViewer viewer;
	
	private Swagger swaggerInfo;
	
	/**
	 * 페이지 타이틀 설정
	 * @param eclipsePjtName
	 */
	public SpecInfoPage(Swagger swaggerInfo){
		super(PAGE_NAME, "Information of Specification file", null);
		this.swaggerInfo = swaggerInfo;
	}
	
	/**
	 * 최초에 control 생성 
	 */
	public void createControl(Composite parent){
//		Composite topLevel = new Composite(parent, SWT.NONE);
//		
//		viewer = new TreeViewer(topLevel);
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setContentProvider(new TreeContentProvider());
        viewer.getTree().setHeaderVisible(true);
        viewer.getTree().setLinesVisible(true);

        TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
        viewerColumn.getColumn().setWidth(300);
        viewerColumn.getColumn().setText("Names");
        viewerColumn.setLabelProvider(new ColumnLabelProvider());

        viewer.setInput(swaggerInfo);

        GridLayoutFactory.fillDefaults().generateLayout(parent);
        
//		topLevel.setLayout(new GridLayout(3, false));
//		
//		button = new Button(topLevel, SWT.CHECK);
//		button.setSelection(true);
////		boolean check = button.getSelection();
//		
//		Label label = new Label(topLevel, SWT.CENTER);
//		label.setText("조회조건: 프로젝트 명 ");
//		
//		projectNameText = new Text(topLevel, SWT.SINGLE | SWT.BORDER);
//		projectNameText.setEnabled(true);
//		projectNameText.setTextLimit(255);
//		projectNameText.setFont(topLevel.getFont());
		setControl(parent);
		setPageComplete(true);
	}
	
	public boolean useDefaultDirectory(){
		return button.getSelection();
	}

}

class RestAPIViewVO extends TreeNode{
	String resouceName;
	String apiName;
	String apiPath;
	String method;
	List<RestAPIViewVO> children;
	
	boolean hasChild;
	boolean checked;
	
	public RestAPIViewVO(Object value) {
		super(value);
	}
	
	public TreeNode[] getChildren() {
		return (TreeNode[]) this.children.toArray();
	}
	
	public String getResouceName() {
		return resouceName;
	}

	public void setResouceName(String resouceName) {
		this.resouceName = resouceName;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}

class TreeContentProvider implements ITreeContentProvider
{
    public Object[] getChildren(Object parentElement) {
        return ((RestAPIViewVO)parentElement).getChildren();
    }

    public Object getParent(Object element) {
        return ((RestAPIViewVO)element).getParent();
    }

    public boolean hasChildren(Object element) {
        return ((RestAPIViewVO)element).hasChildren();
    }

    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}

class TreeLabelProvider extends LabelProvider
{
    public String getText(Object element) {
        return ((RestAPIViewVO)element).getApiName();
    }
}


