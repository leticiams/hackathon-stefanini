angular.module("hackaton-stefanini").controller("PessoaIncluirAlterarController", PessoaIncluirAlterarController);
PessoaIncluirAlterarController.$inject = [
    "$rootScope",
    "$scope",
    "$location",
    "$q",
    "$filter",
    "$routeParams",
    "HackatonStefaniniService"];

function PessoaIncluirAlterarController(
    $rootScope,
    $scope,
    $location,
    $q,
    $filter,
    $routeParams,
    HackatonStefaniniService) {

    /**ATRIBUTOS DA TELA */
    const vm = this;
    vm.isEdicao = false;

    vm.pessoa = {
        id: null,
        nome: "",
        email: "",
        dataNascimento: null,
        enderecos: [],
        perfils: [],
        situacao: false,
        imagem: null
    };

    vm.enderecoDefault = {
        id: null,
        idPessoa: "",
        cep: "",
        uf: "",
        localidade: "",
        bairro: "",
        logradouro: "",
        complemento: ""
    };

    vm.urlCep = "http://localhost:8081/treinamento/api/enderecos/buscar/";
    vm.urlEndereco = "http://localhost:8081/treinamento/api/enderecos/";
    vm.urlPerfil = "http://localhost:8081/treinamento/api/perfis/";
    vm.urlPessoa = "http://localhost:8081/treinamento/api/pessoas/";

    vm.teste = function () {
        console.log(vm.pessoa);
    }

    /**METODOS DE INICIALIZACAO */
    vm.init = function () {

        vm.tituloTela = "Cadastrar Pessoa";
        vm.acao = "Cadastrar";


        /**Recuperar a lista de perfil */
        vm.listar(vm.urlPerfil).then(
            function (response) {
                if (response !== undefined) {
                    vm.listaPerfil = response;
                    if ($routeParams.idPessoa) {
                        vm.tituloTela = "Editar Pessoa";
                        vm.acao = "Editar";
                        vm.isEdicao = true;

                        vm.recuperarObjetoPorIDURL($routeParams.idPessoa, vm.urlPessoa).then(
                            function (pessoaRetorno) {
                                if (pessoaRetorno !== undefined) {
                                    vm.pessoa = pessoaRetorno;
                                    vm.pessoa.dataNascimento = vm.formataDataTela(pessoaRetorno.dataNascimento);
                                    vm.perfil = vm.pessoa.perfils[0];
                                    vm.pessoa.perfils = [];
                                }
                            }
                        );
                    }
                }
            }
        );
    };

    /**METODOS DE TELA */

    vm.buscaCep = function() {
        HackatonStefaniniService.listar(vm.urlCep + vm.enderecoDefault.cep).then(
            function(response){
                vm.enderecoDefault.cidade = response.data.cidade;
                vm.enderecoDefault.uf = response.data.uf;
                vm.enderecoDefault.localidade = response.data.localidade;
                vm.enderecoDefault.bairro = response.data.bairro;
                vm.enderecoDefault.logradouro = response.data.logradouro;
            }
        )
    }

    vm.visualizarImg = function() {
        var preview = document.querySelectorAll("img").item(0);
        var file = document.querySelector('input[type=file').files[0];

        var reader = new FileReader();

        reader.onloadend = function() {
            preview.src = reader.result;
        };
        if(file) {
            aux = true;
            reader.readAsDataURL(file);
        }
        else {
            preview.src = "";
        }
    }

    vm.cancelar = function () {
        vm.retornarTelaListagem();
    };

    vm.retornarTelaListagem = function () {
        $location.path("listarPessoas");
    };

    vm.abrirModal = function (endereco) {
        vm.enderecoModal = vm.enderecoDefault;
        if (endereco !== undefined)
            vm.enderecoModal = endereco;

        if (vm.pessoa.enderecos.length === 0)
            vm.pessoa.enderecos.push(vm.enderecoModal);

        $("#modalEndereco").modal();
    };

    vm.limparTela = function () {
        $("#modalEndereco").modal("toggle");
        vm.endereco = undefined;
    };

    vm.incluir = function () {

        vm.pessoa.imagem = document.getElementById("ImagemPessoa").getAttribute("src");
        document.getElementById("ImagemPessoa").src = vm.pessoa.imagem;

        var objetoDados = angular.copy(vm.pessoa);
        objetoDados.dataNascimento = vm.formataDataJava(vm.pessoa.dataNascimento);

        var listaEndereco = [];
        angular.forEach(objetoDados.enderecos, function (value, key) {
            if (value.complemento.length > 0) {
                value.idPessoa = objetoDados.id;
                listaEndereco.push(angular.copy(value));
            }
        });

        objetoDados.enderecos = listaEndereco;
        if (vm.perfil !== null){

            var isNovoPerfil = true;
            
            angular.forEach(objetoDados.perfils, function (value, key) {
                if (value.id === vm.perfil.id) {
                    isNovoPerfil = false;
                }
            });
            if (isNovoPerfil)
                objetoDados.perfils.push(vm.perfil);
        }
        if (vm.acao == "Cadastrar") {

            vm.salvar(vm.urlPessoa, objetoDados).then(
                function (pessoaRetorno) {
                    vm.retornarTelaListagem();
                });
        } else if (vm.acao == "Editar") {
            vm.alterar(vm.urlPessoa, objetoDados).then(
                function (pessoaRetorno) {
                    vm.retornarTelaListagem();
                });
        }
    };

    vm.remover = function (objeto, tipo) {
        if (objeto.id){
            var url = vm.urlPessoa + objeto.id;
            if (tipo === "ENDERECO")
                url = vm.urlEndereco + objeto.id;

            vm.excluir(url).then(
                function (ojetoRetorno) {
                    vm.init()
                    alert("Endereço removido com sucesso!")
                }); 
        } else {
            vm.pessoa.enderecos.splice(index, 1)
        }
    };

    /**METODOS DE SERVICO */
    vm.recuperarObjetoPorIDURL = function (id, url) {

        var deferred = $q.defer();
        HackatonStefaniniService.listarId(url + id).then(
            function (response) {
                if (response.data !== undefined)
                    deferred.resolve(response.data);
                else
                    deferred.resolve(vm.enderecoDefault);
            }
        );
        return deferred.promise;
    };

    vm.listar = function (url) {

        var deferred = $q.defer();
        HackatonStefaniniService.listar(url).then(
            function (response) {
                if (response.data !== undefined) {
                    deferred.resolve(response.data);
                }
            }
        );
        return deferred.promise;
    }

    vm.salvar = function (url, objeto) {

        var deferred = $q.defer();
        var obj = JSON.stringify(objeto);
        HackatonStefaniniService.incluir(url, obj).then(
            function (response) {
                if (response.status == 200) {
                    deferred.resolve(response.data);
                } else {
                    vm.pessoa.dataNascimento = vm.formataDataTela(vm.pessoa.dataNascimento)
                }
                if (vm.urlEndereco === url) {
                    vm.init();
                }
            }
        );
        return deferred.promise;
    }

    vm.alterar = function (url, objeto) {

        var deferred = $q.defer();
        var obj = JSON.stringify(objeto);
        HackatonStefaniniService.alterar(url, obj).then(
            function (response) {
                if (response.status == 200) {
                    deferred.resolve(response.data);
                }
                if (vm.urlEndereco === url) {
                    vm.init();
                }
            }
        );
        return deferred.promise;
    }

    vm.excluir = function (url, objeto) {

        var deferred = $q.defer();
        HackatonStefaniniService.excluir(url).then(
            function (response) {
                if (response.status == 200) {
                    deferred.resolve(response.data);
                }
            }
        );
        return deferred.promise;
    }

    vm.listaUF = [
        { "id": "RO", "desc": "RO" },
        { "id": "AC", "desc": "AC" },
        { "id": "AM", "desc": "AM" },
        { "id": "RR", "desc": "RR" },
        { "id": "PA", "desc": "PA" },
        { "id": "AP", "desc": "AP" },
        { "id": "TO", "desc": "TO" },
        { "id": "MA", "desc": "MA" },
        { "id": "PI", "desc": "PI" },
        { "id": "CE", "desc": "CE" },
        { "id": "RN", "desc": "RN" },
        { "id": "PB", "desc": "PB" },
        { "id": "PE", "desc": "PE" },
        { "id": "AL", "desc": "AL" },
        { "id": "SE", "desc": "SE" },
        { "id": "BA", "desc": "BA" },
        { "id": "MG", "desc": "MG" },
        { "id": "ES", "desc": "ES" },
        { "id": "RJ", "desc": "RJ" },
        { "id": "SP", "desc": "SP" },
        { "id": "PR", "desc": "PR" },
        { "id": "SC", "desc": "SC" },
        { "id": "RS", "desc": "RS" },
        { "id": "MS", "desc": "MS" },
        { "id": "MT", "desc": "MT" },
        { "id": "GO", "desc": "GO" },
        { "id": "DF", "desc": "DF" }
    ];

    vm.salvarEndereco = function () {

        if(vm.enderecoNovo.id){
            vm.alterar(vm.urlEndereco, angular.copy(vm.enderecoNovo));
        } else {

            if(vm.isEdicao) {
                vm.enderecoNovo.idPessoa = vm.pessoa.id
                vm.salvar(vm.urlEndereco, angular.copy(vm.enderecoNovo));
            } else {
                vm.pessoa.enderecos.push(angular.copy(vm.enderecoNovo));
            }
        }

        vm.enderecoNovo = {
            cep: null,
            uf: null,
            localidade: null,
            bairro: null,
            logradouro: null,
            complemento: null
        }
    };
}