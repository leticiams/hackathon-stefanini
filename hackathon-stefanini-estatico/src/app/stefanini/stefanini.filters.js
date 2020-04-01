(function (angular) {
    'use strict';

    var mesesDoAno = ['jan', 'fev', 'mar', 'abr', 'mai', 'jun', 'jul', 'ago', 'set', 'out', 'nov', 'dez'];

    /*
     * Converte uma chave de usuário em matrícula
     */
    var converterMatricula = function() {
        return function(chave) {
            return parseInt(chave.replace(/\D/g, ''));
        };
    };

    /**
     * Converte para float
     */
    var converterFloat = function() {
        return function(numero) {
            if (numero) {
                return parseFloat(numero.replace(',', '.'));
            }
        };
    };

    /**
     * Converte Inteiro para Float
     */
    var converterInteiroParaFloat = function() {
        return function(numero) {
            if (numero) {
                return parseFloat(numero);
            }
        };
    };

    /*
     * Formata uma chave de usuário a partir de uma matrícula
     */
    var formatarChave = function() {
        return function(matricula) {
            matricula = matricula + '';
            return 'F' + ('0000000' + matricula).substring(matricula.length);
        };
    };

    /*
     * Adiciona caracteres à esquerda até completar o tamanho informado
     */
    var lpad = function() {
        return function(str, length, padString) {
            str = str + '';
            padString = padString || '0';
            while (str.length < length)
                str = padString + str;
            return str;
        };
    };

    var mesAno = function() {
        return function(data, padrao) {
            data = data + '';

            if (!data) {
                return padrao || '';
            }

            var mes = parseInt(data.substring(3, 5));
            var ano = data.substring(6, 10);

            if (mes > 0 && ano !== '') {
                return mesesDoAno[mes - 1] + '/' + ano;
            }
            return data;
        };
    };

    var capitalizeFirstWord = function() {
        return function(text) {
            if (!text) return '';
            return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
        };
    };

    var capitalizeEachWord = function() {
        return function(text) {
            if (!text) return '';
            var cfw = capitalizeFirstWord();
            return text.replace(/[a-zA-Z\u00C0-\u00FF]+/gi, function(l) {
                return cfw(l);
            });
        };
    };

    var formataMCI = function() {
        return function(text) {
            if (!text) return '';
            var dado = '' + text;
            while (dado.length < 11) {
                dado = "0" + dado;
            }
            dado = dado.substring(0, 3) + '.' + dado.substring(3, 6) + '.' + dado.substring(6, 9) + '-' + dado.substring(9, 11);
            return dado;
        };
    };

    angular.module('stefanini.filters', ['ngSanitize'])
        .filter('converterMatricula', converterMatricula)
        .filter('formatarChave', formatarChave)
        .filter('lpad', lpad)
        .filter('mesAno', mesAno)
        .filter('capitalizeFirstWord', capitalizeFirstWord)
        .filter('capitalizeEachWord', capitalizeEachWord)
        .filter('converterFloat', converterFloat)
        .filter('converterInteiroParaFloat', converterInteiroParaFloat);

})(angular);