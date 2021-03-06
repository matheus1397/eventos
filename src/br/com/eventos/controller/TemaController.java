package br.com.eventos.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.eventos.dao.GenericDAOException;
import br.com.eventos.dao.TemaDAO;
import br.com.eventos.dao.impl.TemaDAOImpl;
import br.com.eventos.model.Tema;
import br.com.eventos.model.Usuario;

/**
 * Servlet implementation class TemaController
 */
@WebServlet("/TemaController")
public class TemaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TemaController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario user = (Usuario)session.getAttribute("USUARIO_LOGADO");
		if (!(user == null)) {
			response.sendRedirect("./cadastro_tema.jsp");
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}else {
			response.getWriter().append("Vc não tá logado seu merdinha");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String cmd = request.getParameter("cmd");
		String msg = null;
		HttpSession session = request.getSession();
		try {
			TemaDAO tDAO = new TemaDAOImpl();
			if ("cadastrar".equals(cmd)) {
				Tema t = new Tema();

				t.setDescricao(request.getParameter("txtdescricao"));

				tDAO.adicionar(t);
				msg = "Tema cadastrado com sucesso!!";
				session.setAttribute("MENSAGEM", msg);
				response.sendRedirect("./cadastro_concluido.jsp");

			} else if ("pesquisar".equals(cmd)) {
				List<Tema> lista = tDAO.pesquisarporTema(request.getParameter("txtdescricao"));
				session.setAttribute("LISTA", lista);
				msg = "Atualmente temos" + lista.size() + " temas cadastrados";
				session.setAttribute("MENSAGEM", msg);
				response.sendRedirect("./cadastro_tema.jsp");
			}

		} catch (GenericDAOException | NumberFormatException e) {
			e.printStackTrace();
			msg = "erro ao adicionar este tema";
		}
		
	}
}
