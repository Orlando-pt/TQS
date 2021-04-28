# aula 06 SonarQube

# Slide picking a mantra
- é necessário manter o código. testar testar testar
- é preciso crir código que seja mais fácil de manter

# Porquê fazer refactoring
- código mais limpo
- avisos relativos à segurança
- fazer o código mais reusável
- fazer um desenho melhor porque percebi melhor o modelo. então faço um refactoring

# Static Code Analysis
- podemos confiar nas knowledge bases cridas ao longo dos anos
- confiar numa ferrament que olhe para o source code e verifique problemas lendo o código

# Code inspection
- analisar código sem colocar nada a correr
- verificar qualidade intrínseca do código
	- variáveis criadas que não são usadas
- verificar código premisquo a sql injection

# Lint
- ferramenta para correr static code analysis

# vulnerabilidades do código
- estão documentadas no cwe

# ferramentas completas para analisar código
- SonarQube
- Codacy

# SonarQube
- vulnerabilities
	- todas as situaçòes relactionadas com segurança
	
- bug
	- problema na lógica de código
	- uma variável com valor nulo

- code smell
	- construções do código que não estão necessáriamente erradas mas que tornam o código mais difícil de manter
	
# Quality gates
- são portões onde um push só passa se as especificações forem cumpridas

# complexidade métrica
- tem a ver com o número de caminhos independentes que existem para percorrer uma determinada classe
- uma forma de calculr a complexidade é ver as arestas e subtrair os nós + 2
- dá-nos uma pist de quants situações de teste teria de ter para testar todos os caminhos possíveis deste algoritmo
- se tiver uma complexidade muito grande vai ser bastante difícil manter esta classe

# Integrar sonar no CI
- associar sonarLint ao próprio IDE
- associar também ao github ou gitlab aquando do CI
- a fazer o build também se pode colocar lá
