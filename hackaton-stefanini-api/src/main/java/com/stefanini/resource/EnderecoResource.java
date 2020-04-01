package com.stefanini.resource;

import com.stefanini.dto.SucessoDto;
import com.stefanini.model.Endereco;
import com.stefanini.servico.EnderecoServico;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Path("enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    private static Logger log = Logger.getLogger(EnderecoResource.class.getName());


    /**
     * Classe de servico da Pessoa
     */
    @Inject
    private EnderecoServico enderecoServico;
    /**
     *
     */
    @Context
    private UriInfo uriInfo;


    /**
     *
     * @return
     */
    @GET
    public Response obterEnderecos() {
        log.info("Obtendo lista de pessoas");

        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        Optional<List<Endereco>> listPessoa = enderecoServico.getList();
        return listPessoa.map(enderecos -> Response.ok(enderecos).build()).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     *
     * @param endereco
     * @return
     */
    @POST
    public Response adicionarEndereco(@Valid Endereco endereco) {
        return Response.ok(enderecoServico.salvar(endereco)).build();
    }


    /**
     *
     * @param endereco
     * @return
     */
    @PUT
    public Response atualizarEndereco(@Valid Endereco endereco) {
        return Response.ok(enderecoServico.atualizar(endereco)).build();
    }


    /**
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("{id}")
    public Response deletarEndereco(@PathParam("id") Long id) {
        if(enderecoServico.encontrar(id).isPresent()){
            enderecoServico.remover(id);
            return Response.status(Response.Status.OK).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    public Response obterEndereco(@PathParam("id") Long id) {
        return enderecoServico.encontrar(id).map(endereco -> Response.ok(endereco).build()).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

}
