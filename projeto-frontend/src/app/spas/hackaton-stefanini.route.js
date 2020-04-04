  
(function(angular){
    "use strict";

    angular.module('hackaton-stefanini').config(function ($routeProvider) {
        $routeProvider

            /** Rota para Home */
            .when('/', {
                templateUrl: 'app/spas/homePage/template/home.tpl.html',
                controller: 'HomeController as vm'
            })
            /** Rotas para Pessoas */
            .when('/listarPessoas', {
                templateUrl: 'app/spas/pessoas/template/pessoa-listar.tpl.html',
                controller: 'PessoaListarController as vm'
            })
            .when('/EditarPessoas/:idPessoa', {
                templateUrl: 'app/spas/pessoas/template/pessoa-incluir-alterar.tpl.html',
                controller: 'PessoaIncluirAlterarController as vm'
            })
            .when('/cadastrarPessoa', {
                templateUrl: 'app/spas/pessoas/template/pessoa-incluir-alterar.tpl.html',
                controller: 'PessoaIncluirAlterarController as vm'
            })
            /** Rotas para Perfis */
            .when('/listarPerfis', {
                templateUrl: 'app/spas/perfis/template/perfil-listar.tpl.html',
                controller: 'PerfilListarController as vm'
            })
            .when('/EditarPerfis', {
                templateUrl: 'app/spas/perfis/template/perfil-incluir-alterar.tpl.html',
                controller: 'PerfilIncluirAlterarController as vm'
            })
            .when('/cadastrarPerfis', {
                templateUrl: 'app/spas/perfis/template/perfil-incluir-alterar.tpl.html',
                controller: 'PerfilIncluirAlterarController as vm'
            })
            .when('/EditarPerfis/:idPerfil', {
                templateUrl: 'app/spas/perfis/template/perfil-incluir-alterar.tpl.html',
                controller: 'PerfilIncluirAlterarController as vm'
            })
            .when('/listarEnderecos', {
                templateUrl: 'app/spas/enderecos/template/endereco-listar.tpl.html',
                controller: 'EnderecoListarController as vm'
            })
            .when('/cadastrarEnderecos', {
                templateUrl: 'app/spas/enderecos/template/endereco-incluir.alterar.tpl.html',
                controller: 'EnderecoIncluirAlterarController as vm'
            })
            
            .otherwise({
                templateUrl: 'index_ERROR.html'
            });
    });

}(angular));

