angular.module("hackaton-stefanini").controller("PessoaListarController", PessoaListarController);
PessoaListarController.$inject = ["$rootScope", "$scope", "$location",
    "$q", '$filter', '$routeParams', 'HackatonStefaniniService'];

function PessoaListarController($rootScope, $scope, $location,
    $q, $filter, $routeParams, HackatonStefaniniService) {
    vm = this;

    vm.qdePorPagina = 5;
    vm.ultimoIndex = 0;
    vm.contador = 0;

    vm.url = "http://localhost:8081/treinamento/api/pessoas/";
    vm.urlEndereco = "http://localhost:8081/treinamento/api/enderecos/";

    vm.init = function () {
        HackatonStefaniniService.listar(vm.url).then(
            function (responsePessoas) {
                if (responsePessoas.data !== undefined)
                    vm.listaPessoas = responsePessoas.data;

                vm.listaPessoasMostrar = [];
                var max = vm.listaPessoas.length > vm.qdePorPagina ? vm.qdePorPagina : vm.listaPessoas.length;

                vm.qdePaginacao = new Array(vm.listaPessoas.length % vm.qdePorPagina === 0 ? vm.listaPessoas.length / vm.qdePorPagina : parseInt(vm.listaPessoas.length / vm.qdePorPagina) + 1);
                vm.currentPage = 1;
                for (var count = 0; count < max; count++) {
                    vm.listaPessoasMostrar.push(vm.listaPessoas[count]);
                    vm.ultimoIndex++;
                }

                vm.listaPessoasMostrar.sort(function (a, b) {
                    return a.id - b.id;
                });

                HackatonStefaniniService.listar(vm.urlEndereco).then(
                    function (responseEndereco) {
                        if (responseEndereco.data !== undefined)
                            vm.listaEndereco = responseEndereco.data;
                    }
                );
            }
        );
    };

    vm.atualizarPaginanacao = function (index) {

        if (index >= vm.currentPage)
            vm.avancarPaginanacao(index);
        else
            vm.retrocederPaginanacao(index);
    };

    vm.avancarPaginanacao = function (index) {
        
        vm.listaPessoasMostrar = [];
        vm.currentPage++;

        var idx = angular.copy(vm.ultimoIndex);
        var cont = vm.listaPessoas.length - vm.qdePorPagina;
        for (var count = cont > vm.qdePorPagina ? vm.qdePorPagina : cont; count > 0; count--) {
            vm.listaPessoasMostrar.push(vm.listaPessoas[idx++]);
            vm.ultimoIndex++;
            vm.contador++;
        }
        vm.listaPessoasMostrar.sort(function (a, b) {
            return a.id - b.id;
        });
    };

    vm.retrocederPaginanacao = function (index) {
        
        vm.listaPessoasMostrar = [];
        vm.currentPage--;

        var idx = vm.contador - 1;
        vm.ultimoIndex = idx + 1;
        for (var count = vm.qdePorPagina; count > 0; count--) {
            vm.listaPessoasMostrar.push(vm.listaPessoas[idx--]);
            vm.contador--;
        }
        vm.listaPessoasMostrar.sort(function (a, b) {
            return a.id - b.id;
        });
    };

    vm.editar = function (id) {
        if (id !== undefined)
            $location.path("EditarPessoas/" + id);
        else
            $location.path("cadastrarPessoa");
    }

    vm.remover = function (id) {

        var liberaExclusao = true;

        angular.forEach(vm.listaEndereco, function (value, key) {
            if (value.idPessoa === id)
                liberaExclusao = false;
        });

        if (liberaExclusao)
            HackatonStefaniniService.excluir(vm.url + id).then(
                function (response) {
                    if(response.mensagem) {
                        alert(response.mensagem);
                    } else {
                        vm.init();
                    }
                }
            );
        else {
            alert("Pessoa com Endereço vinculado, exclusão não permitida");
        }
    }

    vm.retornarTelaListagem = function () {
        $location.path("listarPessoas");
    }

}