package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public EditServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	/*編集画面表示*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();

		String messageGetId = request.getParameter("message");

		//messageの宣言をnullで行う
		Message message = null;

		//idが空白でないか、数字以外の文字でないか
		//チェックがokだった場合、selectに進む
		if (!(StringUtils.isBlank(messageGetId)) && (messageGetId.matches("^[0-9]+$"))) {
			int messageId = Integer.parseInt(messageGetId);
			message = new MessageService().select(messageId);
		}

		//select結果をチェック(入力したidが存在しているか)
		if (message == null) {
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

		//1件分のメッセージを格納
		request.setAttribute("message", message);
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}

	/*編集画面(更新)*/
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		List<String> errorMessages = new ArrayList<String>();
		Message message = new Message();

		message.setId(Integer.parseInt(request.getParameter("id")));
		message.setText(request.getParameter("text"));

		if (!isValid(message, errorMessages)) {
				//isValidでとってきたエラーメッセージ
				request.setAttribute("errorMessages", errorMessages);
				request.setAttribute("message", message);
				request.getRequestDispatcher("edit.jsp").forward(request, response);
				return;
		}

		//isValidでエラーメッセージを取得しなかった場合
		new MessageService().update(message);

		response.sendRedirect("./");
	}

	/*バリデーション*/
	private boolean isValid(Message message, List<String> errorMessages) {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		String text = message.getText();

		if (StringUtils.isBlank(text)) {
			errorMessages.add("入力してください");
		} else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}
}