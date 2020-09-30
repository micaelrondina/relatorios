package br.com.dxc.cards.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import br.com.dxc.cards.dao.ArquivoDAO;
import br.com.dxc.cards.enuns.PermissaoDxcRelatorio;
import br.com.dxc.cards.model.Arquivo;
import br.com.dxc.cards.util.ApiError;
import br.com.dxc.cards.util.Utils;

@Controller
@RequestMapping("/rel-ger-filas-emissor")
public class RelGerFilasEmissorController {
	private static final Logger logger = LogManager.getLogger(RelGerFilasEmissorController.class);

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> listar(HttpServletRequest request) {
		ResponseEntity<Object> ret = null;
		List<Arquivo> arquivos = null;

		try {
			if (!Utils.usuarioTemAcesso(request, PermissaoDxcRelatorio.REL_GER_FILAS_EMISSOR)) {
				ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Sem acesso", "Sem acesso");
				return new ResponseEntity<Object>(apiError, apiError.getStatus());
			}
					
			arquivos = new ArquivoDAO().getListRelGerFilasEmissor();
			ret = new ResponseEntity<Object>(arquivos, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("[DXC-RELATORIOS] Erro inesperado no processamento da requisicao GET /rel-ger-filas-emissor/listar!",
					ex);
			ApiError apiError;
			if (ex instanceof HttpClientErrorException) {
				apiError = new ApiError(((HttpClientErrorException) ex).getStatusCode(), ex.getLocalizedMessage(),
						((HttpClientErrorException) ex).getResponseBodyAsString());
			} else {
				apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ex.getMessage());
			}
			ret = new ResponseEntity<Object>(apiError, apiError.getStatus());

		}
		return ret;
	}

	@RequestMapping(value = "/download/{id_rel}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> download(@PathVariable("id_rel") long idRel,
			HttpServletResponse response, HttpServletRequest request) {
		ResponseEntity<Object> ret = null;

		try {
			if (!Utils.usuarioTemAcesso(request, PermissaoDxcRelatorio.REL_GER_FILAS_EMISSOR)) {
				ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Sem acesso", "Sem acesso");
				return new ResponseEntity<Object>(apiError, apiError.getStatus());
			}
			
			Arquivo arq = new ArquivoDAO().getFileDownload(idRel);
			if (arq == null) {
				response.getOutputStream().write("Nao ha dados para gerar o relatorio.".getBytes());
			} else {
		        response.setContentType("application/x-zip-compressed"); //definindo o tipo do arquivo
		        response.setContentLength(arq.getConteudo().length); //setando o tamanho do arquivo
		        response.setHeader("Content-Disposition","attachment; filename=\"" + arq.getNomeRelat() +"\""); //add nome arquivo que sera baixado. Incluir extensao do arquivo 
		        FileCopyUtils.copy(arq.getConteudo(), response.getOutputStream()); //add o arquivo em si
			}

		} catch (Exception ex) {
			logger.error("[DXC-RELATORIOS] Erro inesperado no processamento da requisicao GET /rel-ger-filas-emissor/download!",
					ex);
			ApiError apiError;
			if (ex instanceof HttpClientErrorException) {
				apiError = new ApiError(((HttpClientErrorException) ex).getStatusCode(), ex.getLocalizedMessage(),
						((HttpClientErrorException) ex).getResponseBodyAsString());
			} else {
				apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ex.getMessage());
			}
			ret = new ResponseEntity<Object>(apiError, apiError.getStatus());

		}
		return ret;
	}
}
