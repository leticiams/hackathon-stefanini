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
        dataHoraInclusao: null
    };

    vm.perfilDefault = {
        id: null,
        nome: "",
        descricao: "",
        dataHoraInclusao: ""
    };

    vm.perfilNovo = {
        nome: "",
        descricao: "",
        dataHoraInclusao: ""
    };

    vm.urlPerfil = "http://localhost:8081/treinamento/api/perfis/";
    vm.isEdicao = false;


    vm.init = function () {
        console.log("teste");
        vm.tituloTela = "Cadastrar Perfil";
        vm.acao = "Cadastrar";

        vm.listar(vm.urlPerfil).then(
            function (response) {
                if (response !== undefined) {
                    vm.listaPerfil = response;
                    if ($routeParams.idPerfil) {
                        vm.tituloTela = "Editar Perfil";
                        vm.acao = "Editar";
                        vm.isEdicao = true;

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

    vm.recuperarObjetoPorIDURL = function (id, url) {

        var deferred = $q.defer();
        HackatonStefaniniService.listarId(url + id).then(
            function (response) {
                if (response.data !== undefined)
                    deferred.resolve(response.data);
                else
                    deferred.resolve(vm.perfilDefault);
            }
        );
        return deferred.promise;
    };

    vm.incluir = function () {
        var objetoDados = angular.copy(vm.perfil);
        vm.perfil.dataHoraInclusao = new Date()
        objetoDados.dataHoraInclusao = new Date(
            vm.perfil.dataHoraInclusao.toISOString(0, 26)
        )

        if (vm.acao == "Cadastrar") {
            HackatonStefaniniService.incluir(vm.urlPerfil, objetoDados).then(
                function (perfilRetorno) {
                    vm.retornarTelaListagem();
                    console.log("cadastrado teste123");
                });
        } else if (vm.acao == "Editar") {
            vm.alterar(vm.urlPerfil, objetoDados).then(
                function (perfilRetorno) {
                    vm.retornarTelaListagem();
                });
        }
    };

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

        vm.cancelar = function () {
            vm.retornarTelaListagem();
        };

        vm.retornarTelaListagem = function () {
            $location.path("listarPerfis");
        };

        vm.salvarPerfil = function () {
            vm.perfil.push(angular.copy(vm.perfilNovo))
            vm.perfilNovo = {
                nome: "",
                descricao: "",
                dataHoraInclusao: ""
            }
        }
    }
