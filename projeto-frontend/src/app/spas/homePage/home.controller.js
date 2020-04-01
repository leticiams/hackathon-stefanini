angular.module("hackaton-stefanini").controller("HomeController", HomeController);
HomeController.$inject = ["$rootScope", "$scope", "$location",
    "$q", '$filter', '$routeParams', 'HackatonStefaniniService'];

function HomeController($rootScope, $scope, $location,
    $q, $filter, $routeParams, HackatonStefaniniService) {
    vm = this;
    vm.ola = "Olá Mundo!!!";

    vm.retornarTelaListagem = function () {
        //$location.path("listagem/100000");
        vm.executaConsultaModelo();
    };

    vm.executaConsultaModelo = function () {
        var dados = {

        };

        HackatonStefaniniService.teste(dados).then(
            function (response) {
                if (response.data !== undefined)
                    console.log(response.data);
            }
        );
    };
}
