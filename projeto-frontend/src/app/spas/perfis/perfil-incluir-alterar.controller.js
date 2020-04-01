angular.module("hackaton-stefanini").controller("PerfilIncluirAlterarController", PerfilIncluirAlterarController);
PerfilIncluirAlterarController.$inject = [
    "$rootScope",
    "$scope",
    "$location",
    "$q",
    "$filter",
    "$routeParams",
    "HackatonStefaniniService"];

function PerfilIncluirAlterarController(
    $rootScope,
    $scope,
    $location,
    $q,
    $filter,
    $routeParams,
    HackatonStefaniniService) {

    /**ATRIBUTOS DA TELA */
    vm = this;

    vm.perfil = {
        id: null,
        nome: "",
        descricao: "",
        dataHrInclusao: null,
        dataHrAlteracao: null,
    };

    vm.urlPerfil = "http://localhost:8081/treinamento/api/perfils/";

    vm.init = function () {



        vm.tituloTela = "Cadastro de Perfil";
        vm.acao = "Cadastrar";


        vm.listar(vm.urlPerfil).then(
            function (response) {
                if (response !== undefined) {
                    vm.listaPerfil = response;
                    if ($routeParams.idPerfil) {
                        vm.tituloTela = "Editar Pessoa";
                        vm.acao = "Editar";

                        vm.recuperarObjetoPorIDURL($routeParams.idPerfil, vm.urlPerfil).then(
                            function (perfilRetorno) {
                                if (perfilRetorno !== undefined) {
                                    vm.perfil = perfilRetorno;
                                }
                            }
                        );
                    }
                }
            }
        );
    };
    vm.incluir = function () {
        var objetoDados = angular.copy(vm.perfil);


        if (vm.acao == "Cadastrar") {
            HackatonStefaniniService.incluir(vm.urlPerfil, objetoDados).then(
                function (perfilRetorno) {
                    console.log("A ação até agora é: " + vm.acao == "Cadastrar");
                });
        } else if (vm.acao == "Editar") {
            vm.alterar(vm.urlPerfil, objetoDados).then(
                function (perfilRetorno) {
                    vm.retornarTelaListagem();
                });
        }

    };

    vm.remover = function (objeto, tipo) {

        var url = vm.urlPerfil + objeto.id;
        vm.excluir(url).then(
            function (ojetoRetorno) {
                vm.retornarTelaListagem();
            });

    };

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

    vm.alterar = function (url, objeto) {

        var deferred = $q.defer();
        var obj = JSON.stringify(objeto);
        HackatonStefaniniService.alterar(url, obj).then(
            function (response) {
                if (response.status == 200) {
                    deferred.resolve(response.data);
                }
            }
        );
        return deferred.promise;
    }
}