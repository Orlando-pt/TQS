# lab08 Orlando Macedo 94521

# Task 1
1. fazer download do ficheiro .war na página de downloads do jenkins
2. correr com java -jar jenkins.war --httpPort=8080
3. ir ao localhost:8080 e colocar a password
4. instalar os plugins sugeridos
5. criar o admin user
6. instalar o plugin de **maven integration** e **pipeline maven integration**
7. configurar o jdk e maven nas global tool configuration
8. a partir de agora podemos criar um job
9. adicionar à configuração o repo git
10. adicionar o caminho para o pom a partir do repo inserido

- o jenkins é uma ferramenta completamente orientada a plugins
- se houver subprojetos no repositório, é necessário colocar o caminho para o pom pretendido

# Task 2 jenkinsfile
1. criar um novo job para projetos **pipeline**
2. configurar para que o trigger de construção seja despoletado por um **Jenkisfile**
3. criar um ficheiro **Jenkinsfile** na raiz do projeto
4. fazer a build e verificar na consola que a versão do java e maven são displayed
5. configurar SCM polling (source control management)
	- This option instructs Jenkins to obtain your Pipeline from Source Control Management (SCM), which will be your locally cloned Git repository.
	- o exemplo usado foi de 5 em 5 minutos para verificar o que estava a acontecer
	- para o everyday work -> H H(8-15)/2 * * 1-5 (de duas em duas horas, durante a semana, e, usando um horário que poderia ser o de um trabalhador vulgar)
6. verificar que ficheiros forma criados pelo jenkins
	- jenkins tem uma cópia íntegra do repositório original
	- tendo também um diretório temporário
	
- para correr teste num sub-projeto:
	- dir('nome-do-projeto') {
		sh 'mvn clean install'
	}
	> Running in /home/orlando/.jenkins/workspace/task2-lab08/lab02-stack
	> + mvn clean install
	> Finished: SUCCESS
	
- apesar do SCM ir fazendo pull do repositório, o mesmo só testa o código se houver um novo commit
	- assim não se gastam recursos desnecessariamente
	
- na dashboard é possível verificar quando foi a útlima vez que um teste correu sem problemas
	- e também quando foi a última vez que teve problemas ( faz referência a qual foi a build )
	
# Task 3 blue ocean
- o Blue Ocean tem uma interface mais amigável

1. escolhemos o job que queremos analisar
2. nesse job temos as várias build efetuadas
	- incluí referência ao último commit
	- a duração da build
	- e há quanto tempo decorreu a determinada build
	- podemos voltar a correr uma certa build
3. escolhendo a build que gostariamos de analisar
	- verifica-se qual foi a fase do pipeline onde terminou
	- log detalhado com todas as operações referentes ao pipeline
4. podemos verificar quais foram as alterações feitas nos ficheiros
5. verificar também os vários testes efetuados
