# Orlando Macedo 94521

# Exercício 1
- os screenshots relativos a este exercício estão na pasta 'screenshots/exercicio1'

## bugs
- tem um bug associado
	- descrição : Save and re-use this "Random"
	- resolver: o uso do construtor de Random parece ser inseguro. Como alternativa, pode-se ir buscar uma instância de Random a partir da Classe SecureRandom (tem print na pasta de screenshots)
- este projeto não apresenta vulnerabilidades
- também não apresenta falhas de segurança
- não existem code smells a reportar
	- (tinha um associado ao Sytem.err que foi utilizado para resolver o bug do Random, aconselhava a utilizar um Logger)

# Exercício 2
## technical debt
- este valor é uma estimativa por parte do SonarQube que dá uma ideia de quanto tempo será necessário para resolver todos os problemas associados ao projeto

- Inicialmente, tinha uma technical debt de 2 horas e 9 minutos
- Depois de resolver o problema referente ao uso do construtor Random(), esse technical debt passou para 0

- Neste momento, 4 linhas de código não estão a ser testadas
- Nenhuma condition a relatar

# Exercício 3
- o projeto IES utilizado encontra-se no repositório github https://github.com/LuisValentim1/IES_Projeto_93989_94521_93168_89034

- os screenshots relativos a este exercício encontram-se me 'screenshots/exercicio3'

- primeiramente foi criado um Quality Gate (print -> quality-gate.png)
	- nesta fase foram específicadas as caraterísticas mínimas que o código deve ter para passar num teste de qualidade
	- será de realçar que para passar, há um campo que específica que todos os testes devem ser completos sem erros (condition -> Unit Test Failures)

- no que diz respeito ao código em si, apresenta 19 bugs, 1 falha de segurança, 107 code smells, e nenhuma coverage, pois não existem testes (print -> reliability-check.png)

## resolução de problemas
### resolver probelma de segurança
- começou por se resolver o problema de segurança, devido à maior gravidade (print -> sec-warning.png)
- o código que permitiu resolver o problema encontra-se no print "refactor-sec-warning.png"
- após o refactor pode verificar-se que o problema de segurança deixa de ser apresentado no dashboard (print -> after-refactor-sec.png)

### resolver bugs
- Como se pode visualizar no print "bug-problems.png", existem 19 bugs que devem ser solucionados
- irá resolver-se o primeiro bug (print -> isPresent-bug.png)
	- para tal irá seguir-se a recomendação dada pelo SonarQube, que indica para verificar primeiro se se encontra algum valor dentro do objeto **Optional**
	- usando o método _orElseThrow()_ é possível fazer essa verificação (print -> refactor-Optional-bug.png)
	- visualizando novamente o dashboard, pode verificar-se que apenas existem mais 17 bugs por resolver. Portanto, menos 2 bugs (foi resolvido um problema semelhante que se encontrava umas linhas abaixo). (print -> after-refactor-Optional-bug.png)

- resolução do bug de dividir por zero ( print -> div-by-zero-bug.png)
	- a ajuda que foi dada, encaminhava no sentido de verificar primeiramente se uma determinada variável tinha o valor de zero, antes dessa mesma variável ser usada como denominador numa divisão
	- a resolução do bug encontra-se no print "resolve-div-by-zero-bug.png"
	- a atualização do dashboard do SonarQube encontra-se no print "after-resolving-div-by-zero-bug.png"
	
	
- por último, foram adicionados testes dummy. Com vista a verificar uma maior coverage do código
	- o print com a classe de testes criada corresponde a "test-example.png"
	- o resultado desses testes é apresentado na dashboard, verificando-se uma maior cobertura (print -> after-test-dashboard.png)
	- de notar que a coverage passou de 3.5% para 4.1% (portanto, passou a cobrir-se mais código). Para além disso, foram adicionados quatro testes unitários, sendo apresentados 5 TU no momento da experiência
	
