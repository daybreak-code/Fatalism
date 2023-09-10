package cn.daycode.fatalism.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {


	public static void responseOut(HttpServletResponse response, String s) {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter pw = response.getWriter()) {
			pw.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
