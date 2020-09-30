package br.com.dxc.cards.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import br.com.dxc.cards.dao.ExtratoECDAO;
import br.com.dxc.cards.model.ApiRequest;
import br.com.dxc.cards.model.ApiResponse;
import br.com.dxc.cards.model.ExtratoEC;
import br.com.dxc.cards.model.FiltroConsultar;
import br.com.dxc.cards.util.ApiError;

@Controller
@RequestMapping("/extrato-ec")
public class ExtratoECController {

	private static final Logger logger = LogManager.getLogger(ExtratoECController.class);

	@RequestMapping(value = "/listar", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ApiResponse<List<ExtratoEC>>> listar(
			@RequestBody ApiRequest<FiltroConsultar> request) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ApiResponse<List<ExtratoEC>> ret = new ApiResponse<>();
		HttpStatus httpStatus = null;

		try {
			httpStatus = HttpStatus.OK;

			ret.setDados(new ExtratoECDAO().getListClubSaude(request.getFiltro().getAcquirerId(),
					request.getFiltro().getDataInicio(), request.getFiltro().getDataFim(),
					request.getFiltro().getComercio(), request.getFiltro().getRubrica(),
					request.getFiltro().getProduto(), request.getFiltro().getFilial()));

		} catch (Exception ex) {
			logger.error("[DXC-RELATORIOS] Erro inesperado no processamento da requisicao POST /extrato-ec/listar!", ex);
			if (ex instanceof HttpClientErrorException) {
				new ApiError(((HttpClientErrorException) ex).getStatusCode(), ex.getLocalizedMessage(),
						((HttpClientErrorException) ex).getResponseBodyAsString());
			} else {
				new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ex.getMessage());
				ret.setFalha(new ApiError(httpStatus, ex.getLocalizedMessage(),
						((HttpClientErrorException) ex).getResponseBodyAsString()));
			}

		}

		return new ResponseEntity<ApiResponse<List<ExtratoEC>>>(ret, headers, httpStatus);
	}

}
