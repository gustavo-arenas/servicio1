package ar.com.ecco.esb.procesos.error;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ErrorProcessor implements Processor {

	@SuppressWarnings({"unchecked", "rawtypes" })
	public void process(Exchange exchange) throws CamelException {

		LinkedHashMap mapaRepuesta = new LinkedHashMap();

		ArrayList listaArray = new ArrayList();
		LinkedHashMap mapRegistro = new LinkedHashMap();

		LinkedHashMap mapCampoEstado = new LinkedHashMap();

		
		mapCampoEstado.put("estado", "\"NoOK\"");
		mapCampoEstado.put("codigorespuesta", "\"1\"");
		mapCampoEstado.put("mensajerespuesta",
				"\"" + exchange.getIn().getHeader("DescError") + "\"");

		
		mapRegistro.put("comprobantes", listaArray);
		
		mapaRepuesta.put("datosestado", mapCampoEstado);
		mapaRepuesta.put("datosrespuesta", mapRegistro);
		//mapRegistro = new LinkedHashMap();
		
		exchange.getIn().setBody(mapaRepuesta);

		Response response = Response
				.status(Integer.parseInt(exchange.getIn().getHeader("CodError")
						.toString())).entity(mapaRepuesta.toString()).build();

		exchange.setOut(exchange.getIn());
		throw new WebApplicationException(response);

	}
}
