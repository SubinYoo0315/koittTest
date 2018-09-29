package common.controller;
//핸들러 정보를 담고 있는 프로퍼티에서 커맨드와 핸들러클래스 정보를 가져와야함.
//URI 요청에 따라 핸들러 객체로 process 메소드를 실행!

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ModifyRequest;
import common.handler.CommandHandler;
import common.handler.NullHandler;


public class BoardController extends HttpServlet{
	//명령에 해당되는 핸들러 객체를 담아두기 위해 맵을 선언.
	//핸들러와 커맨드 명령어 정보가 있는 프로퍼티 파일을 프로퍼티 객체에 담기!
		private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();

		@Override
		public void init() throws ServletException {
			//프로퍼티파일에 있는 값을 꺼내 올 프로퍼티 객체를 생성.
			Properties prop = new Properties();
			//getInitParameter("configFile")를 통해 프로퍼티 파일의 위치값을 가져오고
			//getRealPath를 통해 실제 시스템 경로를 가져왐
			String configFilePath = getServletContext().getRealPath(getInitParameter("handlerConfigFile"));
			//파일에서 프로퍼티로 내용를 가져옴!
			try (FileReader fr = new FileReader(configFilePath)) {
				prop.load(fr);//<String, String> -> <명령어, 핸들러클래스풀네임>
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//프로퍼티에 담긴 아이를 hMap에 객체로 만들어서 담음
			for (Object key : prop.keySet()) {
				String command = (String) key; //command에 각각의 명령어가 한번씩 들어감.
				try {
					//클래스 명을 통해서 핸들러클래스의 정보가 있는 객체를 받아옴
					Class handlerClass = Class.forName(prop.getProperty(command));
					//handlerClass 객체는 메소드들, 생성자들, 클래스이름, 클래스 변수 등등 클래스에 관한
					//정보를 담고 있는 객체다.
					//handlerClass객체를 통해 CommandHandler 형태의 객체를 생성함.
					CommandHandler handlerInstance = 
							(CommandHandler) handlerClass.getDeclaredConstructor().newInstance();
					//사용하기 좋게 맵에 담아둠.
					commandHandlerMap.put(command, handlerInstance);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		//여기서는 하나로 통합해서 작업을 하고
		//추후에 핸들러에서 구분을 하여 로직을 분기처리할 예정.
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			process(req, resp);
		}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			process(req, resp);
		}

		//실제로 동작하는 부분!!
		private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String command = req.getRequestURI();
			//앞을 짤라내고 주소뒤 명령부분만 남김
			if(command.indexOf(req.getContextPath()) == 0) {
				command = command.substring(req.getContextPath().length());
				//subString(시작 인덱스) -> 시작인덱스부터~끝까지를 반환.
			}
			System.out.println("수정후 : " + command);
			//결과를 보여줄 화면주소
			String viewPage = null;

			//핸들러 객체를 담을 핸들러 변수
			CommandHandler handler = null;
			
			if(command == null) {
				//명령어가 없을 때 NullHandler를 통해 처리!
				handler = new NullHandler();
			}else {
				//명령어가 들어오면 해당 명령어를 키로하는 맵에서 핸들러 객체를 받아옴!
				handler = commandHandlerMap.get(command);
			}
			//핸들러를 통해 요청에 해당되는 작업를 하고 결과 화면 주소를 받아옴.
			try {
				viewPage = handler.process(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//요청을 viewPage로 전달해 줌.
			//결과 페이지로 전환시킴
			if(viewPage != null) {
				if(req.getAttribute("modReq") != null) {
					System.out.println("test modify"+((ModifyRequest)req.getAttribute("modReq")).getArticleId());
				}
				RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, resp);
			}
		}
}
