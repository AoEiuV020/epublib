package nl.siegmann.epublib.epub;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(Enclosed.class)
public class DOMUtilTest {

	public static class GetText {
		@Test
		public void test_cdata() throws Exception {
			String input = "<?xml version=\"1.0\"?><doc><title><![CDATA[foo]]></title></doc>";
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(input)));
			Element e = (Element) document.getElementsByTagName("title").item(0);
			String text = DOMUtil.getTextChildrenContent(e);
			assertEquals("foo", text);
		}
	}

	public static class GetAttribute {

		@Test
		public void test_simple_foo() {
			// given
			String input = "<?xml version=\"1.0\"?><doc xmlns:a=\"foo\" xmlns:b=\"bar\" a:myattr=\"red\" b:myattr=\"green\"/>";

			try {
				Document document = EpubProcessorSupport.createDocumentBuilder().parse(new InputSource(new StringReader(input)));
				
				// when
				String actualResult = DOMUtil.getAttribute(document.getDocumentElement(), "foo", "myattr");
				
				// then
				assertEquals("red", actualResult);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
		
		@Test
		public void test_simple_bar() {
			// given
			String input = "<?xml version=\"1.0\"?><doc xmlns:a=\"foo\" xmlns:b=\"bar\" a:myattr=\"red\" b:myattr=\"green\"/>";

			try {
				Document document = EpubProcessorSupport.createDocumentBuilder().parse(new InputSource(new StringReader(input)));
				
				// when
				String actualResult = DOMUtil.getAttribute(document.getDocumentElement(), "bar", "myattr");
				
				// then
				assertEquals("green", actualResult);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
}
