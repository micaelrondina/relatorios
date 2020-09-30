package br.com.dxc.cards.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import br.com.dxc.cards.dao.AdquirenteDAO;
import br.com.dxc.cards.model.ApiResponse;
import br.com.dxc.cards.model.Parametro;
import br.com.dxc.cards.util.ApiError;

@Controller
public class AdquirenteController {

	private static final Logger logger = LogManager.getLogger(AdquirenteController.class);

	@RequestMapping(value = "/adquirentes", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ApiResponse<List<Parametro>>> getAdquirentes() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ApiResponse<List<Parametro>> ret = new ApiResponse<>();

		HttpStatus httpStatus = null;

		try {

			httpStatus = HttpStatus.OK;

			ret.setDados(new AdquirenteDAO().getListAdquirentes());

		} catch (Exception ex) { // [fortify] Aplicacao pequena, tratamento simplificado para excecao
			logger.error("fail /adquirentes", ex);
			if (ex instanceof HttpClientErrorException) {
				httpStatus = ((HttpClientErrorException) ex).getStatusCode();
				ret.setFalha(new ApiError(httpStatus, ex.getLocalizedMessage(),
						((HttpClientErrorException) ex).getResponseBodyAsString()));
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				ret.setFalha(new ApiError(httpStatus, ex.getLocalizedMessage(), ex.getMessage()));
			}
		}

		return new ResponseEntity<ApiResponse<List<Parametro>>>(ret, headers, httpStatus);
	}

}
