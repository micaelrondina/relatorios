package br.com.dxc.cards.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import br.com.dxc.cards.proxy.AccessControlProxy;
import br.com.dxc.cards.util.ApiError;
import br.com.dxc.cards.util.ApiResponseMessage;

@Controller
public class LogoffController {

	private static final Logger logger = LogManager.getLogger(LogoffController.class);

	@RequestMapping(value = "/logoff", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> Logoff(HttpServletRequest request) throws Exception {
		ResponseEntity<Object> ret = null;
		try {			
		    request.getSession().invalidate(); //invalidando a secao
		    ApiResponseMessage apiMessage = new ApiResponseMessage(HttpStatus.OK, AccessControlProxy.getAccessControlLoginPage()); //retornando a url da page de login
		    ret = new ResponseEntity<Object>(apiMessage, HttpStatus.OK);
		    
		} catch(Exception ex) {
			logger.error("[CMS-ACQUIRER-DATAENTRY] Erro inesperado no processamento da requisicao GET /Logoff!", ex);
			ApiError apiError;
			if (ex instanceof HttpClientErrorException) {
				apiError = new ApiError(((HttpClientErrorException) ex).getStatusCode(),
						ex.getLocalizedMessage(), ((HttpClientErrorException) ex).getResponseBodyAsString());
			} else {
				apiError = new ApiError(null, ex.getLocalizedMessage(), ex.getMessage());
			}
			ret = new ResponseEntity<Object>(apiError, apiError.getStatus());
		}
		
		return ret;

	}

}
