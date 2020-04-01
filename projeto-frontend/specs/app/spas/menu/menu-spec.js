describe('MenuController', function(){
    var PessoaTeste;
    var $location, HackatonStefaniniService;

    beforeEach(angular.mock.module('hackaton-stefanini'));

    beforeEach(inject(function(_$location_, _HackatonStefaniniService_,_$controller_, _$httpBackend_){
        $controller = _$controller_;
        $location = _$location_;
        HackatonStefaniniService = _HackatonStefaniniService_;
        $httpBackend = _$httpBackend_;
    }));

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });


})