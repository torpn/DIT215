package dit215;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class testClass {

	public static void main(String[] args) throws IOException {
		
		String url = "http://www.alktweb.goteborg.se/Default.aspx?flik=2";
        
		Document activePage;
	
		activePage = Jsoup.connect(url).timeout(0).get();
		Element con = activePage.body();
		Element textCon = con.getElementById("ctl00_DG1_Hyperlink2_1");
		String html = textCon.html();
		
		System.out.println(html);
	}
}
