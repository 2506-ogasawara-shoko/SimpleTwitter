package chapter6.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

@WebFilter(urlPatterns = { "/edit", "/setting" })
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//型変換
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;

		HttpSession session = (httpReq).getSession();
		User user = (User) session.getAttribute("loginUser");

		/*
		 * （ログインフィルター）
		 * ログインユーザーの情報が取得できなければ
		 * エラーメッセージが追加されログインページへ
		 */
		if (user != null) {
			chain.doFilter(request, response);
		} else {
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("ログインをしてください");
			session.setAttribute("errorMessages", errorMessages);
			httpRes.sendRedirect("/SimpleTwitter/login");
			return;
		}

	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}
}
