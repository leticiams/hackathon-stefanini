describe('Filtros', function() {

    // Carrega os filtros do arh
    beforeEach(module('stefanini.filters'));

    var $filter;

    beforeEach(inject(function(_$filter_) {
        $filter = _$filter_;
    }));

    describe('Converter chave em uma matrícula', function() {

        it('retorna 429676 quando informado F' + '0429676', function() {
            // Arrange
            var converterMatricula = $filter('converterMatricula');
            var chave = 'F' + '0429676';

            // Act
            var matricula = converterMatricula(chave);

            // Assert
            expect(matricula).toBe(429676);
        });

    });

    describe('Formatar uma chave a partir de uma matrícula', function() {

        it('retorna F' + '0429676 quando informado 429676', function() {
            // Arrange
            var formatarChave = $filter('formatarChave');
            var matricula = 429676;

            // Act
            var chave = formatarChave(matricula);

            // Assert
            expect(chave).toBe('F' + '0429676');
        });

    });

    describe('Adiciona caracteres à esquerda até completar o tamanho informado', function() {

        it('retorna 00123 quando informado (123, 5)', function() {
            // Arrange
            var lpad = $filter('lpad');
            var str = '123';
            var length = 5;
            // padString não informada

            // Act
            var textoRetorno = lpad(str, length);

            // Assert
            expect(textoRetorno).toBe('00123');
        });

        it('retorna ##123 quando informado (123, 5, #)', function() {
            // Arrange
            var lpad = $filter('lpad');
            var str = '123';
            var length = 5;
            var padString = '#';

            // Act
            var textoRetorno = lpad(str, length, padString);

            // Assert
            expect(textoRetorno).toBe('##123');
        });

    });

    describe('Formatar uma data em mês/ano', function() {

        it('retorna jan/2017 quando informado 01/01/2017', function() {
            // Arrange
            var mesAno = $filter('mesAno');
            var data = '01/01/2017';

            // Act
            var dataMesAno = mesAno(data);

            // Assert
            expect(dataMesAno).toBe('jan/2017');
        });

        it('retorna dez/1985 quando informado 01/12/1985', function() {
            // Arrange
            var mesAno = $filter('mesAno');
            var data = '01/12/1985';

            // Act
            var dataMesAno = mesAno(data);

            // Assert
            expect(dataMesAno).toBe('dez/1985');
        });

        it('retorna "em andamento" quando informado "" e "em andamento" como texto padrão', function() {
            // Arrange
            var mesAno = $filter('mesAno');
            var data = '';
            var padrao = 'em andamento';

            // Act
            var dataMesAno = mesAno(data, padrao);

            // Assert
            expect(dataMesAno).toBe('em andamento');
        });
    });

    describe('Formatar para que a letra inicial do texto comece com maiúscula', function() {

        it('retorna "Inicio de uma frase" quando informado "inicio de UMA Frase"', function() {
            // Arrange
            var capitalizeFirstWord = $filter('capitalizeFirstWord');
            var texto = 'inicio de UMA Frase';

            // Act
            var textoFormatado = capitalizeFirstWord(texto);

            // Assert
            expect(textoFormatado).toBe('Inicio de uma frase');
        });

    });

    describe('Formatar para que a letra inicial de cadas palavra comece com maiúscula', function() {

        it('retorna "Alex Monteiro Barboza" quando informado "alex MONTEIRO bArboZa"', function() {
            // Arrange
            var capitalizeEachWord = $filter('capitalizeEachWord');
            var texto = 'alex MONTEIRO bArboZa';

            // Act
            var textoFormatado = capitalizeEachWord(texto);

            // Assert
            expect(textoFormatado).toBe('Alex Monteiro Barboza');
        });

        it('retorna "À Noite, Vovô Kowalsky Vê O Ímã Cair No Pé Do Pinguim Queixoso E Vovó Põe Açúcar No Chá De Tâmaras Do Jabuti Feliz." quando informado "À noite, vovô Kowalsky vê o ímã cair no pé do pinguim queixoso e vovó põe açúcar no chá de tâmaras do jabuti feliz."', function() {
            // Arrange
            var capitalizeEachWord = $filter('capitalizeEachWord');
            var texto = 'À noite, vovô Kowalsky vê o ímã cair no pé do pinguim queixoso e vovó põe açúcar no chá de tâmaras do jabuti feliz.';

            // Act
            var textoFormatado = capitalizeEachWord(texto);

            // Assert
            expect(textoFormatado).toBe('À Noite, Vovô Kowalsky Vê O Ímã Cair No Pé Do Pinguim Queixoso E Vovó Põe Açúcar No Chá De Tâmaras Do Jabuti Feliz.');
        });

    });
});