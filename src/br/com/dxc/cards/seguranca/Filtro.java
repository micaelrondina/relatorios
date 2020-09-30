package br.com.dxc.cards.seguranca;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.dxc.cards.model.LoggedUserBean;
import br.com.dxc.cards.proxy.AccessControlProxy;

public class Filtro implements Filter {
	private static final Logger logger = LogManager.getLogger(Filtro.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Credentials", "true");

		if (session.getAttribute("loggedUserBean") != null) {
			chain.doFilter(request, response);
			return;
		}
		if ((req.getRequestURL() != null) && ((req.getRequestURL().toString()
				.startsWith("http://localhost:8080/CMS-ISSUER-DXC-RELATORIOS/"))
				|| (req.getRequestURL().toString().startsWith("http://localhost:4200/CMS-ISSUER-DXC-RELATORIOS/")))) {
			logger.info("modo desenv");
			LoggedUserBean userBean = new LoggedUserBean();

			userBean.setName("Desenv");
			userBean.setToken("dev_token");
			userBean.setTasks(MockUtil.getAllTasks());

			session.setAttribute("loggedUserBean", userBean);
			chain.doFilter(request, response);
			logger.info("usuario: " + userBean.getName());

			return;
		}
		String accessKey = CookieUtil.getCookieValue((HttpServletRequest) request, "authaccess_v2");
		logger.info("accesskey ok " + accessKey);
		if (accessKey == null) {
			res.setStatus(401);
			res.getWriter().print(AccessControlProxy.getAccessControlLoginPage());
			return;
		}
		String[] authData = accessKey.split("\\|");

		String user = authData[0];
		String token = authData[1];
		List<String> tasks = null;
		try {
			tasks = AccessControlProxy.getUserTasks(user, token, "CMSISSUERDXCRELATORIOS");
		} catch (Exception ex) {
			res.setStatus(401);
			res.getWriter().print("HTTP/401 - Falha no acesso aos dados do AccessControl: " + ex.getMessage());
			logger.error(ex.getMessage(), ex);
			return;
		}
		if ((tasks == null) || (tasks.isEmpty())) {
			res.setStatus(401);
			res.getWriter().print("HTTP/401 - Usuario " + user + " nao tem permissao para acessar este sistema");
			return;
		}
		LoggedUserBean userBean = new LoggedUserBean();
		userBean.setName(user);
		userBean.setToken(token);
		userBean.setTasks(tasks);

		session.setAttribute("loggedUserBean", userBean);

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}
}
