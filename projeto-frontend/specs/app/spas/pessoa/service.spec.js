describe('Conjunto de testes - Hudson', function(){
    var ServiceTeste;

    beforeEach(angular.mock.module('hackaton-stefanini'));

    beforeEach(inject(function(HackatonStefaniniService){
        ServiceTeste = HackatonStefaniniService;
    }))

    it('A service existe?', function(){
        expect(ServiceTeste).toBeDefined();
    })

    it('Testando o Listar', function(){
        expect(ServiceTeste.listar()).toBeDefined();
    })

    it('Testando o Excluir', function(){
        expect(ServiceTeste.excluir()).toBeDefined();
    })

})