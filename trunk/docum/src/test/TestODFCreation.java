package test;

import java.io.File;
import java.net.URI;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.docum.domain.po.common.Container;
import com.docum.service.BaseService;
import com.docum.util.XMLUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@Transactional
public class TestODFCreation extends TestCase {
	private static final String LOCATION = "src/test/testResources";
	private static final String STATEMENT_BEGIN = "{$";
	private static final String STATEMENT_END = "}";
	
	@Autowired
	BaseService baseService;
	Container container;
	
	@Test
	public void testODFCreation() {
		try {
			OdfTextDocument odt = OdfTextDocument.loadDocument(LOCATION + "/testTemplate.odt");
			odt.save(LOCATION + "/testResult.odt");
			odt = OdfTextDocument.loadDocument(LOCATION + "/testResult.odt");
			container = baseService.getObject(Container.class, 3L);
			odt.getPackage().insert(new URI(LOCATION + "/1.png"), "Pictures/1.png", null);
			int l = odt.getContentDom().getChildNodes().getLength();
			for (int i = 0; i < l; i++) {
				Node node = odt.getContentDom().getChildNodes().item(i);
				processNode(node);
			}
			//odt.getTableByName("HeaderTable").getCellByPosition(1, 1). setStringValue("!!!!");
			odt.save(LOCATION + "/testResult.odt");
			OpenOfficeConnection officeConnection = new SocketOpenOfficeConnection(8100);
			officeConnection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(officeConnection);
			converter.convert(
				new File("e:/Work/NCSP/Development/docum/project/src/test/testResources/testResult.odt"), 
				new File("e:/Work/NCSP/Development/docum/project/src/test/testResources/testResult.pdf"));
			officeConnection.disconnect();
			
			/*PropertyValue[] saveProps = new PropertyValue[1];
			saveProps[0] = new PropertyValue();
			saveProps[0].Name="FilterName";
			saveProps[0].Value="writer_pdf_Export";			 
			XComponentContext xContext = Bootstrap.bootstrap();
			com.sun.star.lang.XMultiComponentFactory xMCF = xContext.getServiceManager();
			XStorable myStorable = (XStorable)UnoRuntime.queryInterface(XStorable.class, odt);
			myStorable.storeToURL("file:///B:/testResult.pdf", saveProps);*/ 
		} catch(Exception e) {
			TestCase.fail(e.getMessage());
		}
	}
	
	private void processNode(Node node) throws Exception {
		if (node.getNodeValue() != null) {
			int statementBeginPos = node.getNodeValue().indexOf(STATEMENT_BEGIN);
			if (statementBeginPos != -1) {
				System.out.println(node.getNodeName() + " : " + node.getNodeValue());
				replaceNodeValue(node, node.getNodeValue(), container, statementBeginPos);
			}
		}
		/*if (node instanceof TableTableCellElement) {
			TableTableCellElement tableCellElement = (TableTableCellElement) node;
			TextPElement textPElement = tableCellElement.newTextPElement();
			DrawFrameElement drawFrameElement = textPElement.newDrawFrameElement();
			drawFrameElement.setDrawZIndexAttribute(0);
			drawFrameElement.setDrawStyleNameAttribute("fr1");
			drawFrameElement.setSvgHeightAttribute("6.283cm");
			drawFrameElement.setSvgWidthAttribute("8.371cm");
			DrawImageElement drawImageElement = drawFrameElement.newDrawImageElement();
			drawImageElement.setXlinkHrefAttribute("Pictures/1.png");
			drawImageElement.setXlinkActuateAttribute("onLoad");
			drawImageElement.setXlinkShowAttribute("embed");
			drawImageElement.setXlinkTypeAttribute("simple");
			//System.out.println(node.getNodeName() + " : " + node.getNodeValue());
		}
		if (node instanceof OdfDrawImage) {
			System.out.println(" " + node.getAttributes().getNamedItem("xlink:href").getNodeValue());
		}*/
		if (node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			int length = nodeList.getLength();
			for(int i = 0; i< length; i++) {
				processNode(nodeList.item(i));
			}
		}
	}
	
	private void replaceNodeValue(Node node, String processedValue, 
			Container container, int statementBeginPos) throws Exception {
		String result = processedValue.substring(statementBeginPos + STATEMENT_BEGIN.length(), 
			processedValue.indexOf(STATEMENT_END));
		String props[] = result.split("\\.");
		Object propertyValue = XMLUtil.getObjectProperty(container, props[0]);
		if (propertyValue != null) {
			if (propertyValue instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> objects = (List<Object>) propertyValue;
				StringBuffer stringBuffer = new StringBuffer();
				for (Object object: objects) {
					stringBuffer.append(object).append(", ");
				}
				int length = stringBuffer.length();
				stringBuffer.replace(length - 2, length - 1 , "");
				node.setNodeValue(stringBuffer.toString());
			} else {
				node.setNodeValue(
					XMLUtil.propertyUtilsBean.getNestedProperty(container, result).toString());
			}
		}
	}
}
