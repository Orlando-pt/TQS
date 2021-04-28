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

- no que diz respeito ao código em si, apresenta 19 bugs, 1 falha de segurança, 107 code smells, e, lamentavelmente, nenhuma coverage, pois não existem testes (print -> reliability-check.png)

## resolução de problemas
- começou-se por resolver o problema de segurança, devido à maior gravidade
